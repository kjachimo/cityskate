package com.cityskate.controller;

import com.cityskate.api.EventsApi;
import com.cityskate.model.*;
import com.cityskate.service.EventService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class EventsController implements EventsApi {

    private final EventService eventService;

    public EventsController(EventService eventService) {
        this.eventService = eventService;
    }

    public ResponseEntity<EventPage> getEvents(
            Integer page, Integer size, String from, String to) {
        List<Event> events = eventService.findAll();
        EventPage result = new EventPage()
            .content(events)
            .totalElements((long) events.size())
            .totalPages(1)
            .page(0)
            .size(20);
        return ResponseEntity.ok(result);
    }

    @Override
    public ResponseEntity<Event> createEvent(EventRequest eventRequest) {
        Event created = eventService.create(eventRequest);
        return ResponseEntity.status(201).body(created);
    }

    @Override
    public ResponseEntity<Event> getEventById(Long eventId) {
        return eventService.findById(eventId)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<Event> updateEvent(Long eventId, EventRequest eventRequest) {
        return eventService.update(eventId, eventRequest)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<Void> deleteEvent(Long eventId) {
        if (eventService.delete(eventId)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @Override
    public ResponseEntity<List<UserProfile>> getEventParticipants(Long eventId) {
        return ResponseEntity.ok(new ArrayList<>());
    }

    @Override
    public ResponseEntity<Void> joinEvent(Long eventId) {
        return ResponseEntity.status(201).build();
    }

    @Override
    public ResponseEntity<Void> leaveEvent(Long eventId) {
        return ResponseEntity.noContent().build();
    }
}