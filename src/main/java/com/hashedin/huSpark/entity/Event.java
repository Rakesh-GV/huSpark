package com.hashedin.huSpark.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private int maxOccupancy;
    private BigDecimal price;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private int availableSeats;

    @ManyToOne
    @JoinColumn(name = "event_type_id", nullable = false,referencedColumnName="id")
    private EventType eventType;

    @ManyToOne
    @JoinColumn(name = "theatre_id", nullable = false,referencedColumnName="id")
    private Theatre theatre;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getMaxOccupancy() {
        return maxOccupancy;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public int getAvailableSeats() {
        return availableSeats;
    }

    public EventType getEventType() {
        return eventType;
    }

    public Theatre getTheatre() {
        return theatre;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMaxOccupancy(int maxOccupancy) {
        this.maxOccupancy = maxOccupancy;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public void setAvailableSeats(int availableSeats) {
        this.availableSeats = availableSeats;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    public void setTheatre(Theatre theatre) {
        this.theatre = theatre;
    }
}