package com.codewithus.eventservice.Controller;

import com.codewithus.eventservice.Dto.EventDto;
import com.codewithus.eventservice.Service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/event")
public class EventController {

    private final EventService eventService;

    @PostMapping
    public ResponseEntity<EventDto> createEvent(@RequestBody EventDto eventDto) {
        EventDto savedEvent = eventService.createEvent(eventDto);
        return new ResponseEntity<>(savedEvent, HttpStatus.CREATED);
    }

    @PostMapping("/bulk")
    public ResponseEntity<List<EventDto>> createEvents(@RequestBody List<EventDto> eventDtos) {
        List<EventDto> savedEvents = eventService.createEvents(eventDtos);
        return new ResponseEntity<>(savedEvents, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventDto> getEventById(@PathVariable Long id) {
        EventDto eventDto = eventService.getEventById(id);
        return ResponseEntity.ok(eventDto);
    }

    @GetMapping("/name")
    public ResponseEntity<EventDto> getEventByName(@RequestParam("name") String name) {
        EventDto event = eventService.getEventByName(name);
        return ResponseEntity.ok(event);
    }

    @GetMapping
    public ResponseEntity<List<EventDto>> getAllEvents() {
        List<EventDto> events = eventService.getAllEvents();
        return ResponseEntity.ok(events);
    }

    @GetMapping("/date")
    public ResponseEntity<List<EventDto>> getEventsByDate(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date date) {
        return ResponseEntity.ok(eventService.getEventsByDate(date));
    }

    @GetMapping("/description")
    public ResponseEntity<EventDto> getEventByDescription(@RequestParam String description) {
        EventDto eventDto = eventService.getEventByDescription(description);
        return ResponseEntity.ok(eventDto);
    }

    public ResponseEntity<List<EventDto>> getEventsBySportId(@RequestParam String sportId) {
        return ResponseEntity.ok(eventService.getEventsBySportId(sportId));
    }

    @GetMapping("/siteId")
    public ResponseEntity<List<EventDto>> getEventsBySiteId(@RequestParam Long siteId) {
        return ResponseEntity.ok(eventService.getEventsBySiteId(siteId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EventDto> updateEvent(@PathVariable Long id, @RequestBody EventDto eventDto) {
        EventDto eventDto1 = eventService.updateEvent(id, eventDto);
        return ResponseEntity.ok(eventDto1);

    }

    @PatchMapping("/{id}")
    public ResponseEntity<EventDto> updateEventField(@PathVariable Long id, @RequestBody Map<String, Object> updateFields) throws ParseException {
        if (updateFields.size() != 1) {
            throw new IllegalArgumentException("Only one field can be updated at a time");
        }

        Map.Entry<String, Object> field = updateFields.entrySet().iterator().next();
        EventDto updatedEvent = eventService.updateEventField(id, field.getKey(), field.getValue());
        return ResponseEntity.ok(updatedEvent);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEvent(@PathVariable Long id) {
        eventService.deleteEvent(id);
        return ResponseEntity.ok("Event deleted successfully");
    }

    
}
