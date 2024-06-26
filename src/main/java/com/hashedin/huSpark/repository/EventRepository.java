package com.hashedin.huSpark.repository;

import com.hashedin.huSpark.entity.Event;
import com.hashedin.huSpark.entity.EventType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EventRepository  extends JpaRepository<Event, Long> {

    boolean existsByNameAndStartTimeAndEndTime(String name, LocalDateTime startTime, LocalDateTime endTime);

    List<Event> findByEventTypeAndNameAndTheatre(EventType eventType, String name, String theater);
}
