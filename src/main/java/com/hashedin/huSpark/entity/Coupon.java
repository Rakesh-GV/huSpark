package com.hashedin.huSpark.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
public class Coupon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String code;
    private BigDecimal discount;
    private LocalDateTime expiryDate;
    private String applicableEventType;
    private int usageLimit;

    public Long getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public LocalDateTime getExpiryDate() {
        return expiryDate;
    }

    public String getApplicableEventType() {
        return applicableEventType;
    }

    public int getUsageLimit() {
        return usageLimit;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public void setExpiryDate(LocalDateTime expiryDate) {
        this.expiryDate = expiryDate;
    }

    public void setApplicableEventType(String applicableEventType) {
        this.applicableEventType = applicableEventType;
    }

    public void setUsageLimit(int usageLimit) {
        this.usageLimit = usageLimit;
    }
}
