package com.codewithus.planningservice.Service;

import com.codewithus.planningservice.Dto.PlanningDto;
import java.util.List;

public interface PlanningService {

    PlanningDto createPlanning(PlanningDto planningDto);
    List<PlanningDto> getAllPlannings();
    PlanningDto getPlanningById(Long id);
    PlanningDto updatePlanning(Long id, PlanningDto planningDto);
    void deletePlanning(Long id);
    List<PlanningDto> getPlanningsByEventId(Long eventId);
    List<PlanningDto> getPlanningsByUserId(String userId);

}
