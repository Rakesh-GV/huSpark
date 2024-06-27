package com.hashedin.huSpark.model;

import com.hashedin.huSpark.entity.Event;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class CreateEvent {

    private String name;
    private int maxOccupancy;
    private BigDecimal price;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private int availableSeats;

    private long eventTypeId;
    private long theatreId;
}
