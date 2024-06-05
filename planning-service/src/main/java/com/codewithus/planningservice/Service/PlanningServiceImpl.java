package com.codewithus.planningservice.Service;

import com.codewithus.planningservice.Dto.PlanningDto;
import com.codewithus.planningservice.Exception.ResourceNotFoundException;
import com.codewithus.planningservice.Mapper.PlanningMapper;
import com.codewithus.planningservice.Model.Planning;
import com.codewithus.planningservice.Repository.PlanningRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class PlanningServiceImpl implements PlanningService {

    private final PlanningRepository planningRepository;
    private final WebClient webClient;


    @Override
    public PlanningDto createPlanning(PlanningDto planningDto) {
        // Verify if the event exists in the Event microservice
        return verifyEventExists(planningDto.getEventId())
                .flatMap(exists -> {
                    if (!exists) {
                        return Mono.error(new RuntimeException("Event with ID " + planningDto.getEventId() + " does not exist"));
                    }
                    Planning planning = PlanningMapper.mapToPlanning(planningDto);
                    Planning savedPlanning = planningRepository.save(planning);
                    log.info("Planning created successfully with ID: {}", savedPlanning.getId());
                    return Mono.just(PlanningMapper.mapToDto(savedPlanning));
                })
                .block();
    }

    private Mono<Boolean> verifyEventExists(Long eventId) {
        return webClient.get()
                .uri("http://localhost:8083/api/event/{id}", eventId)
                .retrieve()
                .onStatus(status -> status.value() >= 400 && status.value() < 500, response ->
                        Mono.error(new ResourceNotFoundException("Event with ID " + eventId + " not found")))
                .bodyToMono(Void.class)
                .thenReturn(true)
                .onErrorResume(ResourceNotFoundException.class, e ->
                        Mono.just(false));
    }

    @Override
    public List<PlanningDto> getAllPlannings() {
        List<Planning> plannings = planningRepository.findAll();

        return plannings.stream().map(PlanningMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public PlanningDto getPlanningById(Long id) {
        Planning planning = planningRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Planning not found !"));
        return PlanningMapper.mapToDto(planning);
    }

    @Override
    public Mono<PlanningDto> updatePlanning(Long id, PlanningDto planningDto) {
        return Mono.zip(
                        verifyEventExists(planningDto.getEventId()),
                        verifyUserExists(planningDto.getUserId())
                )
                .flatMap(results -> {
                    boolean eventExists = results.getT1();
                    boolean userExists = results.getT2();

                    if (!eventExists) {
                        return Mono.error(new ResourceNotFoundException("Event with ID " + planningDto.getEventId() + " does not exist"));
                    }

                    if (!userExists) {
                        return Mono.error(new ResourceNotFoundException("User with ID " + planningDto.getUserId() + " does not exist"));
                    }

                    return Mono.justOrEmpty(planningRepository.findById(id))
                            .flatMap(planning -> {
                                planning.setEventId(planningDto.getEventId());
                                planning.setUserId(planningDto.getUserId());
                                return Mono.just(planningRepository.save(planning));
                            })
                            .map(PlanningMapper::mapToDto)
                            .switchIfEmpty(Mono.error(new ResourceNotFoundException("Planning not found with ID: " + id)));
                });
    }


    private Mono<Boolean> verifyUserExists(String userId) {
        return webClient.get()
                .uri("http://localhost:8082/api/user/{id}", userId)
                .retrieve()
                .onStatus(status -> status.value() >= 400 && status.value() < 500, response ->
                         Mono.error(new ResourceNotFoundException("User with ID " + userId + " not found")))
                .bodyToMono(Void.class)
                .thenReturn(true)
                .onErrorResume(e -> Mono.just(false));
    }

    @Override
    public void deletePlanning(Long id) {
        planningRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Planning not found !"));

        planningRepository.deleteById(id);
        log.info("Planning deleted successfully !");
    }

    @Override
    public List<PlanningDto> getPlanningsByEventId(Long eventId) {
        List<Planning> plannings = planningRepository.findByEventId(eventId);
        return plannings.stream().map(PlanningMapper::mapToDto).collect(Collectors.toList());
    }


    @Override
    public List<Map<String, Object>> getPlanningsByUserId(String userId) {
        List<Planning> plannings = planningRepository.findByUserId(userId);
        return plannings.stream()
                .map(planning -> {
                    Long eventId = planning.getEventId();
                    return fetchEventDetails(eventId)
                            .map(eventDetails -> {
                                Map<String, Object> planningDetails = new HashMap<>();
                                planningDetails.put("planning", PlanningMapper.mapToDto(planning));
                                planningDetails.putAll(eventDetails);
                                return planningDetails;
                            })
                            .block();
                })
                .collect(Collectors.toList());
    }

    private Mono<Map<String, Object>> fetchEventDetails(Long eventId) {
        return webClient.get()
                .uri("http://localhost:8083/api/event/{id}", eventId)
                .retrieve()
                .bodyToMono(Map.class)
                .cast(Map.class)
                .onErrorResume(e -> Mono.just(new HashMap<String, Object>()))
                .map(map -> (Map<String, Object>) map);
    }

}
