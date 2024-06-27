package com.hashedin.huSpark.service;

import com.hashedin.huSpark.entity.Coupon;
import com.hashedin.huSpark.entity.Event;
import com.hashedin.huSpark.entity.EventType;
import com.hashedin.huSpark.entity.Theatre;
import com.hashedin.huSpark.model.CreateEvent;
import com.hashedin.huSpark.model.Mapper;
import com.hashedin.huSpark.repository.EventRepository;
import com.hashedin.huSpark.repository.TheaterRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private TheaterRepository theaterRepository;

    @Autowired
    private Mapper mapper;

    public Event createEvent(CreateEvent event) {
        if (eventRepository.existsByNameAndStartTimeAndEndTime(event.getName(), event.getStartTime(), event.getEndTime())) {
            throw new ValidationException("Event with same name and  time already exists");
        }
        return eventRepository.save(mapper.MapToEvent(event));
    }

    public void deleteEvent(Long eventId) {
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new EntityNotFoundException("Event not found"));
        eventRepository.delete(event);
    }

    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

   /* public List<Event> searchEvents(String eventType, String name, String theatre) {
       var theaters = theaterRepository.findByName(theatre);
        return eventRepository.findByEventTypeAndNameAndTheatre(eventType, name, theatre);
    }*/

}

