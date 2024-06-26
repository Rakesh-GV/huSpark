package com.hashedin.huSpark.service;

import com.hashedin.huSpark.entity.EventType;
import com.hashedin.huSpark.repository.EventTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventTypeService {
    @Autowired
    private EventTypeRepository eventTypeRepository;

    public EventType createEventType(EventType eventType) {
        return eventTypeRepository.save(eventType);
    }

    public List<EventType> getAllEventTypes() {
        return eventTypeRepository.findAll();
    }
}
