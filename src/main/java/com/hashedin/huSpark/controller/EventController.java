package com.hashedin.huSpark.controller;

import com.hashedin.huSpark.entity.Event;
import com.hashedin.huSpark.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/events")
public class EventController {
    @Autowired
    private EventService eventService;

    @PostMapping
    public ResponseEntity<Event> createEvent(@RequestBody Event event) {
        Event createdEvent = eventService.createEvent(event);
        return new ResponseEntity<>(createdEvent, HttpStatus.CREATED);
    }

    @DeleteMapping("/{eventId}")
    public ResponseEntity<Void> deleteEvent(@PathVariable Long eventId) {
        eventService.deleteEvent(eventId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @GetMapping("/search")
    public ResponseEntity<List<Event>> searchEvents(@RequestParam(required = false) String eventType, @RequestParam(required = false) String name, @RequestParam(required = false) String theater) {
        List<Event> events = eventService.searchEvents(eventType, name, theater);
        return new ResponseEntity<>(events, HttpStatus.OK);
    }


}


