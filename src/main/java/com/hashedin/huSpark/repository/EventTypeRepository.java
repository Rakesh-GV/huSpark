package com.hashedin.huSpark.repository;

import com.hashedin.huSpark.entity.EventType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventTypeRepository  extends JpaRepository<EventType, Long> {

}
