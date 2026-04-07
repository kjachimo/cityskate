package com.cityskate.entity;

import jakarta.persistence.*;
import java.time.OffsetDateTime;
import java.util.List;

@Entity
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String email;

    @Enumerated(EnumType.STRING)
    private Role role;

    private OffsetDateTime createdAt;

    public enum Role {
        ADMIN,
        SKATER
    }

    @ManyToMany(mappedBy = "participants")
    private List<EventEntity> events;

    // getters/setters

    public Long getId() { return id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public Role getRole() { return role; }
    public void setRole(Role role) { this.role = role; }

    public OffsetDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(OffsetDateTime createdAt) { this.createdAt = createdAt; }

    public List<EventEntity> getEvents() { return events; }
    public void setEvents(List<EventEntity> events) { this.events = events; }
}