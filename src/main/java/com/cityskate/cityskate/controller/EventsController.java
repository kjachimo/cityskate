package com.cityskate.cityskate.controller;

import com.cityskate.api.EventsApi;
import com.cityskate.model.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
public class EventsController implements EventsApi {

    private static final List<Event> events = new ArrayList<>();
    private static long idCounter = 1;

    @Override
    public ResponseEntity<EventPage> getEvents(
            Integer page, Integer size, LocalDate from, LocalDate to) {
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
        Event e = new Event()
            .id(idCounter++)
            .name(eventRequest.getName())
            .startDateTime(eventRequest.getStartDateTime())
            .lat(eventRequest.getLat())
            .lng(eventRequest.getLng());
        events.add(e);
        return ResponseEntity.status(201).body(e);
    }

    @Override
    public ResponseEntity<Event> getEventById(Long eventId) {
        return events.stream()
            .filter(e -> e.getId().equals(eventId))
            .findFirst()
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<Event> updateEvent(Long eventId, EventRequest eventRequest) {
        for (Event e : events) {
            if (e.getId().equals(eventId)) {
                e.setName(eventRequest.getName());
                e.setStartDateTime(eventRequest.getStartDateTime());
                return ResponseEntity.ok(e);
            }
        }
        return ResponseEntity.notFound().build();
    }

    @Override
    public ResponseEntity<Void> deleteEvent(Long eventId) {
        events.removeIf(e -> e.getId().equals(eventId));
        return ResponseEntity.noContent().build();
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