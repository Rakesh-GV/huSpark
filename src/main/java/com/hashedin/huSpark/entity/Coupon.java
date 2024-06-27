package com.hashedin.huSpark.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
public class Coupon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String code;
    private BigDecimal discount;
    private LocalDateTime expiryDate;

    @ManyToOne
    @JoinColumn(name = "event_type_id", nullable = false,referencedColumnName="id")
    private EventType applicableEventType;
    private int usageLimit;
}
