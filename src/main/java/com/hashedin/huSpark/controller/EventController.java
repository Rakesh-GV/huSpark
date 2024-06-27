package com.hashedin.huSpark.controller;

import com.hashedin.huSpark.entity.Event;
import com.hashedin.huSpark.model.CreateEvent;
import com.hashedin.huSpark.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping(path = "/events")
public class EventController {
    @Autowired
    private EventService eventService;

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Event> createEvent(@RequestBody CreateEvent event) {
        Event createdEvent = eventService.createEvent(event);
        return new ResponseEntity<>(createdEvent, HttpStatus.CREATED);
    }

    @GetMapping
    public List<Event> getEvents() {
        return eventService.getEvents();
    }

    @DeleteMapping("/{eventId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> deleteEvent(@PathVariable Long eventId) {
        eventService.deleteEvent(eventId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

   @GetMapping("/search")
    public ResponseEntity<List<Event>> searchEvents(@RequestParam(required = false) String eventType, @RequestParam(required = false) String name, @RequestParam(required = false) String theatre) {
        List<Event> all = eventService.getEvents();

       List<Event> filteredEvents = all.stream().filter(e -> (isNotEmptyString(eventType) && e.getEventType().getName().contains(eventType)) ||
               (isNotEmptyString(name) && e.getName().contains(name)) || (isNotEmptyString(theatre) && e.getTheatre().getName().contains(theatre)) ).toList();
       return new ResponseEntity<>(filteredEvents, HttpStatus.OK);
    }

    private boolean isNotEmptyString(String string) {
        return string != null && !string.isEmpty();
    }



}


