package com.cityskate.controller;

import com.cityskate.api.EventsApi;
import com.cityskate.api.UsersApi;
import com.cityskate.model.Event;
import com.cityskate.model.EventPage;
import com.cityskate.model.EventRequest;
import com.cityskate.model.UserProfile;
import com.cityskate.service.EventService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/events")
public class EventsController implements EventsApi{

    private final EventService eventService;

    public EventsController(EventService eventService) {
        this.eventService = eventService;
    }

    // GET /events
    @GetMapping
    public ResponseEntity<EventPage> getEvents(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "20") Integer size,
            @RequestParam(required = false) String from,
            @RequestParam(required = false) String to
    ) {
        List<Event> events = eventService.findAll();

        EventPage result = new EventPage();
        result.setContent(events);
        result.setTotalElements((long) events.size());
        result.setTotalPages(1);
        result.setPage(page);
        result.setSize(size);

        return ResponseEntity.ok(result);
    }

    // POST /events
    @PostMapping
    public ResponseEntity<Event> createEvent(@RequestBody EventRequest request) {
        Event created = eventService.create(request);
        return ResponseEntity.status(201).body(created);
    }

    // GET /events/{id}
    @GetMapping("/{eventId}")
    public ResponseEntity<Event> getEventById(@PathVariable Long eventId) {
        return eventService.findById(eventId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // PUT /events/{id}
    @PutMapping("/{eventId}")
    public ResponseEntity<Event> updateEvent(
            @PathVariable Long eventId,
            @RequestBody EventRequest request
    ) {
        return eventService.update(eventId, request)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // DELETE /events/{id}
    @DeleteMapping("/{eventId}")
    public ResponseEntity<Void> deleteEvent(@PathVariable Long eventId) {
        if (eventService.delete(eventId)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    // GET /events/{id}/participants
    @GetMapping("/{eventId}/participants")
    public ResponseEntity<List<UserProfile>> getParticipants(@PathVariable Long eventId) {
        return ResponseEntity.ok(new ArrayList<>());
    }

    // POST /events/{id}/participants
    @PostMapping("/{eventId}/participants")
    public ResponseEntity<Void> joinEvent(@PathVariable Long eventId) {
        return ResponseEntity.status(201).build();
    }

    // DELETE /events/{id}/participants
    @DeleteMapping("/{eventId}/participants")
    public ResponseEntity<Void> leaveEvent(@PathVariable Long eventId) {
        return ResponseEntity.noContent().build();
    }
}