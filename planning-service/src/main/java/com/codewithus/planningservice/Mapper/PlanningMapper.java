package com.codewithus.planningservice.Mapper;

import com.codewithus.planningservice.Dto.PlanningDto;
import com.codewithus.planningservice.Model.Planning;

public class PlanningMapper {

    public static Planning mapToevent (PlanningDto planningDto) {
        return new Planning(
                planningDto.getId(),
                planningDto.getUserId(),
                planningDto.getEventId()
        );
    }

    public static PlanningDto mapToDto (Planning planning) {
        return new PlanningDto(
                planning.getId(),
                planning.getUserId(),
                planning.getEventId()
        );
    }
}
