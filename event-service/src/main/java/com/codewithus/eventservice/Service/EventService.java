package com.codewithus.eventservice.Service;


import com.codewithus.eventservice.Dto.EventDto;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

public interface EventService {
    EventDto createEvent(EventDto eventDto);

    List<EventDto> createEvents(List<EventDto> eventDtos);

    EventDto getEventById(Long id);

    EventDto getEventByName(String name);

    void deleteEvent(Long id);

    EventDto updateEventField(Long id, String key, Object value) throws ParseException;

    EventDto updateEvent(Long id, EventDto eventDto);

    List<EventDto> getAllEvents();


    EventDto getEventByDescription(String description);

    List<EventDto> getEventsBySportId(String sportId);

    List<EventDto> getEventsBySiteId(Long siteId);

    List<EventDto> getEventsByDate(Date date);
}
