package com.hashedin.huSpark.dtos;

import com.hashedin.huSpark.entity.Coupon;
import com.hashedin.huSpark.entity.EventType;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class CouponDto {
    private String code;
    private BigDecimal discount;
    private LocalDateTime expiryDate;
    private long eventType;
    private int usageLimit;

    public Coupon toCoupon(EventType applicableTo){
        var coupon = new Coupon();
        coupon.setCode(this.code);
        coupon.setDiscount(this.discount);
        coupon.setUsageLimit(this.usageLimit);
        coupon.setExpiryDate(this.expiryDate);
        coupon.setApplicableEventType(applicableTo);

        return coupon;
    }
}
