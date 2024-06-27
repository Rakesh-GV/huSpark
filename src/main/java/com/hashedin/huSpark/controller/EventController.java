package com.hashedin.huSpark.controller;

import com.hashedin.huSpark.entity.Coupon;
import com.hashedin.huSpark.entity.Event;
import com.hashedin.huSpark.entity.EventType;
import com.hashedin.huSpark.entity.Theatre;
import com.hashedin.huSpark.model.CreateEvent;
import com.hashedin.huSpark.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/events")
public class EventController {
    @Autowired
    private EventService eventService;

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Event> createEvent(@RequestBody CreateEvent event) {
        Event createdEvent = eventService.createEvent(event);
        return new ResponseEntity<>(createdEvent, HttpStatus.CREATED);
    }


    @DeleteMapping("/{eventId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> deleteEvent(@PathVariable Long eventId) {
        eventService.deleteEvent(eventId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/get_all")
    public ResponseEntity<List<Event>> getAllCoupons() {
        List<Event> coupons = eventService.getAllEvents();
        return new ResponseEntity<>(coupons, HttpStatus.OK);
    }
}


