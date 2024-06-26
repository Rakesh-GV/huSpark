package com.hashedin.huSpark.repository;

import com.hashedin.huSpark.entity.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface CouponRepository extends JpaRepository<Coupon, Long>
{
    Optional<Coupon> findByCode(String  code);
}
