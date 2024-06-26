package com.hashedin.huSpark.repository;

import com.hashedin.huSpark.entity.Booking;
import com.hashedin.huSpark.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface BookingRepository extends JpaRepository<Booking,Long>
{
    List<Booking> findByUser(User user);
}

