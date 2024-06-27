package com.hashedin.huSpark.repository;

import com.hashedin.huSpark.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface EventRepository  extends JpaRepository<Event, Long> {

    boolean existsByNameAndStartTimeAndEndTime(String name, LocalDateTime startTime, LocalDateTime endTime);

   /* @Query("Select e.id, e.available_seats, e.end_time, e.max_occupancy, e.name, e.price, e.start_time from event e  " +
            "join event_type et on et.id = e.event_type_id " +
            "join theatre t on e.theatre_id = t.id " +
            "where et.name = :eventType " +
            "and e.name = :name " +
            "and t.name = :theater")
    List<Event> findByEventTypeAndNameAndTheatre(String eventType, String name, String theater);*/
}
