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

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class PlanningServiceImpl implements PlanningService {

    private final PlanningRepository planningRepository;


    @Override
    public PlanningDto createPlanning(PlanningDto planningDto) {
        Planning planning = PlanningMapper.mapToevent(planningDto);
        Planning savedPlanning = planningRepository.save(planning);
        log.info("Planning created successfully !");
        return PlanningMapper.mapToDto(savedPlanning);
    }

    @Override
    public List<PlanningDto> getAllPlannings() {
        List<Planning> plannings = planningRepository.findAll();

        return plannings.stream().map((planning) -> PlanningMapper.mapToDto(planning))
                .collect(Collectors.toList());
    }

    @Override
    public PlanningDto getPlanningById(Long id) {
        Planning planning = planningRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Planning not found !"));
        return PlanningMapper.mapToDto(planning);
    }

    @Override
    public PlanningDto updatePlanning(Long id, PlanningDto planningDto) {
        Planning planning = planningRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Planning not found !"));

        planning.setEventId(planning.getEventId());
        planning.setUserId(planning.getUserId());

        Planning savedPlanning = planningRepository.save(planning);
        return PlanningMapper.mapToDto(savedPlanning);

    }

    @Override
    public void deletePlanning(Long id) {
        Planning planning = planningRepository.findById(id)
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
    public List<PlanningDto> getPlanningsByUserId(String userId) {
        List<Planning> plannings = planningRepository.findByUserId(userId);
        return plannings.stream().map(PlanningMapper::mapToDto).collect(Collectors.toList());
    }
}
