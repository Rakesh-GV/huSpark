package com.hashedin.huSpark.controller;

import com.hashedin.huSpark.entity.Booking;
import com.hashedin.huSpark.entity.User;
import com.hashedin.huSpark.service.BookingService;
import com.hashedin.huSpark.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/bookings")
public class BookingController {
    @Autowired
    private BookingService bookingService;
    @Autowired
    private UserInfoService userInfoService;

    @PostMapping
    public ResponseEntity<Booking> bookTickets(@RequestParam Long eventId, @RequestParam int numberOfSeats, @RequestParam(required = false) String couponCode, Principal principal) {
        User user = userInfoService.findByEmail(principal.getName());
        Booking booking = bookingService.bookTickets(eventId, numberOfSeats, couponCode, user);
        return new ResponseEntity<>(booking, HttpStatus.CREATED);
    }

    @PostMapping("/cancel")
    public ResponseEntity<Booking> cancelBooking(@RequestParam Long bookingId, @RequestParam(required = false) Integer numberOfSeatsToCancel, Principal principal) {
        Booking booking;
        if (numberOfSeatsToCancel == null) {
            booking = bookingService.cancelBooking(bookingId);
        } else {
            booking = bookingService.cancelBooking(bookingId, numberOfSeatsToCancel);
        }
        return new ResponseEntity<>(booking, HttpStatus.OK);
    }
}
