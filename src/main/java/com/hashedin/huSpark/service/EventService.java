package com.hashedin.huSpark.service;

import com.hashedin.huSpark.entity.Event;
import com.hashedin.huSpark.entity.EventType;
import com.hashedin.huSpark.repository.EventRepository;
import com.hashedin.huSpark.repository.EventTypeRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventService {
    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private EventTypeRepository eventTypeRepository;

    public Event createEvent(Event event) {
        if (eventRepository.existsByNameAndStartTimeAndEndTime(event.getName(), event.getStartTime(), event.getEndTime())) {
            throw new ValidationException("Event with same name and  time already exists");
        }
        return eventRepository.save(event);
    }

    public void deleteEvent(Long eventId) {
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new EntityNotFoundException("Event not found"));
        eventRepository.delete(event);
    }


    public List<Event> searchEvents(String eventTypeName, String name, String theater) {
        var eventType = eventTypeRepository.findByEventTypeName(eventTypeName);
        return eventRepository.findByEventTypeAndNameAndTheatre(eventType, name, theater);
    }

}

