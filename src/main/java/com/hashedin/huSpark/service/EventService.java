package com.hashedin.huSpark.service;

import com.hashedin.huSpark.entity.Event;
import com.hashedin.huSpark.repository.EventRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventService {
    @Autowired
    private EventRepository eventRepository;

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


    public List<Event> searchEvents(String eventType, String name, String theater) {
        return eventRepository.findByEventTypeAndNameAndTheatre(eventType, name, theater);
    }

}

