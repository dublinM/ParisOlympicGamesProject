package com.codewithus.eventservice.Service;

import com.codewithus.eventservice.Dto.EventDto;
import com.codewithus.eventservice.Dto.SiteDto;
import com.codewithus.eventservice.Dto.SportResponse;
import com.codewithus.eventservice.Exception.ResourceNotFoundException;
import com.codewithus.eventservice.Mapper.EventMapper;
import com.codewithus.eventservice.Model.Event;
import com.codewithus.eventservice.Repository.EventRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class EventServiceImpl  implements EventService{

    private final EventRepository eventRepository;
    private final WebClient webClient;


    @Override
    public Mono<EventDto> createEvent(EventDto eventDto) {
        String sportId = eventDto.getSportId();
        Long siteId = eventDto.getSiteId();

        Mono<Boolean> sportExists = webClient.get()
                .uri("http://localhost:8080/api/sport/{id}", sportId)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, response -> Mono.error(new RuntimeException("Sport with ID " + sportId + " not found")))
                .bodyToMono(Void.class)
                .thenReturn(true);

        Mono<Boolean> siteExists = webClient.get()
                .uri("http://localhost:8081/api/site/{id}", siteId)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, response -> Mono.error(new RuntimeException("Site with ID " + siteId + " not found")))
                .bodyToMono(Void.class)
                .thenReturn(true);

        return Mono.zip(sportExists, siteExists)
                .publishOn(Schedulers.boundedElastic())
                .publishOn(Schedulers.boundedElastic())
                .flatMap(result -> {
                    if (!result.getT1() || !result.getT2()) {
                        return Mono.error(new RuntimeException("Validation failed for Sport or Site ID"));
                    }
                    Event event = EventMapper.mapToEvent(eventDto);
                    return Mono.just(eventRepository.save(event));
                })
                .map(EventMapper::mapToEventDto);
    }

    @Override
    public Optional<Map<String, Object>> getEventById(Long id) {

        EventDto eventDto = eventRepository.findById(id)
                .map(EventMapper::mapToEventDto)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found with id: " + id));

        SportResponse sport = webClient.get()
                .uri("http://localhost:8080/api/sport/{id}", eventDto.getSportId())
                .retrieve()
                .bodyToMono(SportResponse.class)
                .block();

        SiteDto site = webClient.get()
                .uri("http://localhost:8081/api/site/{id}", eventDto.getSiteId())
                .retrieve()
                .bodyToMono(SiteDto.class)
                .block();

        Map<String, Object> response = new HashMap<>();
        response.put("event", eventDto);
        response.put("sportDetails", sport);
        response.put("siteDetails", site);

        return Optional.of(response);
    }

    @Override
    public List<EventDto> createEvents(List<EventDto> eventDtos) {
        List<Event> events = eventDtos.stream()
                .map(EventMapper::mapToEvent)
                .collect(Collectors.toList());
        events = eventRepository.saveAll(events);
        return events.stream()
                .map(EventMapper::mapToEventDto)
                .collect(Collectors.toList());
    }

    @Override
    public EventDto getEventByName(String name) {
        Event event = eventRepository.findByName(name)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found with name : " + name));
        return EventMapper.mapToEventDto(event);
    }

    @Override
    public void deleteEvent(Long id) {
        Event event = eventRepository.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException("Event not found !"));
        eventRepository.deleteById(id);
        log.info("Event deleted successfully !");
    }

    @Override
    public EventDto updateEventField(Long id, String fieldName, Object value) throws ParseException {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found !"));

        DateFormat df = new SimpleDateFormat("MM/dd/yyyy");

        switch (fieldName) {
            case "name" -> event.setName(fieldName);
            case "date" -> event.setDate(df.parse(fieldName));
            case "description" -> event.setDescription(fieldName);
            case "sportId" -> event.setSportId(fieldName);
            case "siteId" -> event.setSiteId(Long.valueOf(fieldName));
            default -> throw new IllegalArgumentException("Field name is not valid");
        }

        Event updatedEvent = eventRepository.save(event);
        return EventMapper.mapToEventDto(updatedEvent);

    }

    @Override
    public EventDto updateEvent(Long id, EventDto eventDto) {

        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found!"));

        checkResourceExists("sport", eventDto.getSportId());
        checkResourceExists("site", eventDto.getSiteId());

        event.setName(eventDto.getName());
        event.setDate(eventDto.getDate());
        event.setDescription(eventDto.getDescription());
        event.setSiteId(eventDto.getSiteId());
        event.setSportId(eventDto.getSportId());

        Event savedEvent = eventRepository.save(event);
        return EventMapper.mapToEventDto(savedEvent);
    }

    private void checkResourceExists(String resourceType, Object resourceId) {
        WebClient webClient = WebClient.create();
        String formattedId = resourceId.toString();
        Boolean exists = webClient.get()
                .uri("http://localhost:808" + (resourceType.equals("sport") ? "0" : "1") + "/api/" + resourceType + "/{id}", formattedId)
                .retrieve()
                .onStatus(status -> (status.value() >= 400 && status.value() <= 499), response ->
                        Mono.error(new RuntimeException(resourceType + " with ID " + formattedId + " not found")))
                .bodyToMono(Void.class)
                .thenReturn(true)
                .blockOptional()
                .orElseThrow(() -> new RuntimeException(resourceType + " with ID " + formattedId + " not found"));
    }

    @Override
    public List<EventDto> getAllEvents() {
        List<Event> events = eventRepository.findAll();

        return events.stream().map((EventMapper::mapToEventDto))
                .collect(Collectors.toList());
    }

    @Override
    public EventDto getEventByDescription(String description) {
        Event event = eventRepository.findByDescription(description)
                .orElseThrow(() -> new ResourceNotFoundException("Event with description provided not found"));

        return EventMapper.mapToEventDto(event);
    }

    @Override
    public List<EventDto> getEventsBySportId(String sportId) {
        List<Event> events = eventRepository.findBySportId(sportId);
        return events.stream().map(EventMapper:: mapToEventDto).collect(Collectors.toList());
    }

    @Override
    public List<EventDto> getEventsBySiteId(Long siteId) {
        List<Event> events = eventRepository.findBySiteId(siteId);
        return events.stream().map(EventMapper::mapToEventDto).collect(Collectors.toList());
    }

    @Override
    public List<Map<String, Object>> getEventsByDate(Date date) {
        List<Event> events = eventRepository.findByDate(date);
        List<Map<String, Object>> detailedEvents = new ArrayList<>();

        for (Event event : events) {
            EventDto eventDto = EventMapper.mapToEventDto(event);

            SportResponse sport = webClient.get()
                    .uri("http://localhost:8080/api/sport/{id}", eventDto.getSportId())
                    .retrieve()
                    .bodyToMono(SportResponse.class)
                    .block();  // Bloque l'exécution en attendant la réponse

            SiteDto site = webClient.get()
                    .uri("http://localhost:8081/api/site/{id}", eventDto.getSiteId())
                    .retrieve()
                    .bodyToMono(SiteDto.class)
                    .block();  // Bloque l'exécution en attendant la réponse

            Map<String, Object> detailedEvent = new HashMap<>();
            detailedEvent.put("event", eventDto);
            detailedEvent.put("sportDetails", sport);
            detailedEvent.put("siteDetails", site);
            detailedEvents.add(detailedEvent);
        }
        return detailedEvents;
    }
}
