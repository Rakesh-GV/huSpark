package com.hashedin.huSpark.repository;

import com.hashedin.huSpark.entity.EventType;
import com.hashedin.huSpark.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EventTypeRepository  extends JpaRepository<EventType, Long> {
    EventType findByEventTypeName(String eventName);
}
