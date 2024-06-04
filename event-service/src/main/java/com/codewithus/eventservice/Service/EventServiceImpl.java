package com.codewithus.eventservice.Service;

import com.codewithus.eventservice.Dto.EventDto;
import com.codewithus.eventservice.Exception.ResourceNotFoundException;
import com.codewithus.eventservice.Mapper.EventMapper;
import com.codewithus.eventservice.Model.Event;
import com.codewithus.eventservice.Repository.EventRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class EventServiceImpl  implements EventService{

    private final EventRepository eventRepository;


    @Override
    public EventDto createEvent(EventDto eventDto) {
        Event event = EventMapper.mapToEvent(eventDto);
        Event savedEvent = eventRepository.save(event);
        return EventMapper.mapToEventDto(savedEvent);
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
    public EventDto getEventById(Long id) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found !"));
        return EventMapper.mapToEventDto(event);
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
            case "name":
                event.setName(fieldName);
                break;
            case "date":
                event.setDate(df.parse(fieldName));
                break;
            case "description":
                event.setDescription(fieldName);
                break;
            case "sportId":
                event.setSportId(fieldName);
                break;
            case "siteId":
                event.setSiteId(Long.valueOf(fieldName));
                break;
            default:
                throw new IllegalArgumentException("Field name is not valid");
        }

        Event updatedEvent = eventRepository.save(event);
        return EventMapper.mapToEventDto(updatedEvent);

    }

    @Override
    public EventDto updateEvent(Long id, EventDto eventDto) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found !"));

        event.setName(eventDto.getName());
        event.setDate(eventDto.getDate());
        event.setDescription(eventDto.getDescription());
        event.setSiteId(eventDto.getSiteId());
        event.setSportId(eventDto.getSportId());

        Event savedEvent = eventRepository.save(event);

        return EventMapper.mapToEventDto(savedEvent);

    }

    @Override
    public List<EventDto> getAllEvents() {
        List<Event> events = eventRepository.findAll();

        return events.stream().map((event -> EventMapper.mapToEventDto(event)))
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
    public List<EventDto> getEventsByDate(Date date) {
        List<Event> events = eventRepository.findByDate(date);
        return events.stream().map(EventMapper::mapToEventDto).collect(Collectors.toList());
    }



}
