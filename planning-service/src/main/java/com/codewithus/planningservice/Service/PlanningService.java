package com.codewithus.planningservice.Service;

import com.codewithus.planningservice.Dto.PlanningDto;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

public interface PlanningService {

    PlanningDto createPlanning(PlanningDto planningDto);
    List<PlanningDto> getAllPlannings();
    PlanningDto getPlanningById(Long id);
    Mono<PlanningDto> updatePlanning(Long id, PlanningDto planningDto);
    void deletePlanning(Long id);
    List<PlanningDto> getPlanningsByEventId(Long eventId);
    List<Map<String, Object>> getPlanningsByUserId(String userId);

}
