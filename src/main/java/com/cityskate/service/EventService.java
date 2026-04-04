package com.cityskate.service;

import com.cityskate.entity.EventEntity;
import com.cityskate.entity.UserEntity;
import com.cityskate.model.Event;
import com.cityskate.model.EventRequest;
import com.cityskate.model.UserProfile;
import com.cityskate.repository.EventRepository;
import com.cityskate.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EventService {

    private final EventRepository eventRepository;
    private final UserRepository userRepository;

    private static final Long CURRENT_USER_ID = 1L;

    public EventService(EventRepository eventRepository, UserRepository userRepository) {
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
    }

    private Event toDto(EventEntity e) {
        Event dto = new Event();
        dto.setId(e.getId());
        dto.setName(e.getName());
        dto.setDescription(e.getDescription());

        if (e.getStartDateTime() != null)
            dto.setStartDateTime(OffsetDateTime.parse(e.getStartDateTime()));

        if (e.getEndDateTime() != null)
            dto.setEndDateTime(OffsetDateTime.parse(e.getEndDateTime()));

        dto.setLat(e.getLat());
        dto.setLng(e.getLng());
        dto.setLocationName(e.getLocationName());
        dto.setMaxParticipants(e.getMaxParticipants());

        // 🔥 ORGANIZER MAPPING
        if (e.getOrganizer() != null) {
            UserProfile organizerDto = new UserProfile();
            organizerDto.setId(e.getOrganizer().getId());
            organizerDto.setUsername(e.getOrganizer().getUsername());
            organizerDto.setEmail(e.getOrganizer().getEmail());
            organizerDto.setRole(UserProfile.RoleEnum.valueOf(e.getOrganizer().getRole().name()));
            organizerDto.setCreatedAt(e.getOrganizer().getCreatedAt());

            dto.setOrganizer(organizerDto);
        }

        return dto;
    }

    private EventEntity toEntity(EventRequest req) {
        EventEntity e = new EventEntity();
        e.setName(req.getName());
        e.setDescription(req.getDescription());

        if (req.getStartDateTime() != null)
            e.setStartDateTime(req.getStartDateTime().toString());

        if (req.getEndDateTime() != null)
            e.setEndDateTime(req.getEndDateTime().toString());

        e.setLat(req.getLat());
        e.setLng(req.getLng());
        e.setLocationName(req.getLocationName());
        e.setMaxParticipants(req.getMaxParticipants());

        return e;
    }

    public List<Event> findAll() {
        return eventRepository.findAll()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public Event create(EventRequest request) {
        EventEntity entity = toEntity(request);

        UserEntity user = userRepository.findById(CURRENT_USER_ID)
                .orElseThrow(() -> new RuntimeException("User not found"));

        entity.setOrganizer(user);

        EventEntity saved = eventRepository.save(entity);
        return toDto(saved);
    }

    public Optional<Event> findById(Long id) {
        return eventRepository.findById(id).map(this::toDto);
    }

    public Optional<Event> update(Long id, EventRequest request) {
        return eventRepository.findById(id).map(existing -> {
            existing.setName(request.getName());
            existing.setDescription(request.getDescription());

            if (request.getStartDateTime() != null)
                existing.setStartDateTime(request.getStartDateTime().toString());

            if (request.getEndDateTime() != null)
                existing.setEndDateTime(request.getEndDateTime().toString());

            existing.setLat(request.getLat());
            existing.setLng(request.getLng());
            existing.setLocationName(request.getLocationName());
            existing.setMaxParticipants(request.getMaxParticipants());

            return toDto(eventRepository.save(existing));
        });
    }

    public boolean delete(Long id) {
        if (eventRepository.existsById(id)) {
            eventRepository.deleteById(id);
            return true;
        }
        return false;
    }
}