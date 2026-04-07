package com.cityskate.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "rental_points")
public class RentalPointEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Double lat;
    private Double lng;
    private String address;

    // getters/setters

    public Long getId() { return id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Double getLat() { return lat; }
    public void setLat(Double lat) { this.lat = lat; }

    public Double getLng() { return lng; }
    public void setLng(Double lng) { this.lng = lng; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
}