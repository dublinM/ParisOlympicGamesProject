package com.codewithus.eventservice.Mapper;

import com.codewithus.eventservice.Dto.EventDto;
import com.codewithus.eventservice.Model.Event;

public class EventMapper {

    public static EventDto mapToEventDto(Event event) {
        return new EventDto(
                event.getId(),
                event.getName(),
                event.getDate(),
                event.getDescription(),
                event.getSportId(),
                event.getSiteId()
        );
    }

    public static Event mapToEvent (EventDto eventDto) {
        return new Event(
                eventDto.getId(),
                eventDto.getName(),
                eventDto.getDate(),
                eventDto.getDescription(),
                eventDto.getSportId(),
                eventDto.getSiteId()
        );
    }
}
