package com.hashedin.huSpark.repository;

import com.hashedin.huSpark.entity.EventType;
import com.hashedin.huSpark.entity.Theatre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EventTypeRepository  extends JpaRepository<EventType, Long> {

   Optional<EventType> findByName(String name);
}
