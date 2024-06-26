package com.hashedin.huSpark.service;

import com.hashedin.huSpark.entity.*;
import com.hashedin.huSpark.repository.BookingRepository;
import com.hashedin.huSpark.repository.CouponRepository;
import com.hashedin.huSpark.repository.EventRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;

@Service
public class BookingService {
    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private CouponRepository couponRepository;



    public int getAvailableSeats(Long eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new EntityNotFoundException("Event not found"));
        return event.getAvailableSeats();
    }

    public Booking bookTickets(Long eventId, int numberOfSeats, String couponCode, User user) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new EntityNotFoundException("Event not found"));

        if (event.getAvailableSeats() < numberOfSeats) {
            throw new ValidationException("Not enough seats available");
        }

        BigDecimal totalPrice = event.getPrice().multiply(new BigDecimal(numberOfSeats));
        BigDecimal gst = calculateGst(event.getEventType(), user.getDob());
        totalPrice = totalPrice.add(totalPrice.multiply(gst));

        if (couponCode != null) {
            Coupon coupon = couponRepository.findByCode(couponCode)
                    .orElseThrow(() -> new ValidationException("Invalid coupon code"));
            totalPrice = totalPrice.subtract(totalPrice.multiply(coupon.getDiscount().divide(new BigDecimal(100))));
        }

        event.setAvailableSeats(event.getAvailableSeats() - numberOfSeats);
        eventRepository.save(event);

        Booking booking = new Booking(user, event, numberOfSeats, totalPrice);
        return bookingRepository.save(booking);
    }

    private BigDecimal calculateGst(EventType eventType, LocalDate userDob) {
        int userAge = Period.between(userDob, LocalDate.now()).getYears();
        if (userAge > 60) return BigDecimal.ZERO;

        switch (eventType.getName()) {
            case "Movies":
                return new BigDecimal("0.08");
            case "Concerts":
                return new BigDecimal("0.10");
            case "Live_Show":
                return new BigDecimal("0.06");
            default:
                throw new ValidationException("Invalid event type");
        }
    }

    public Booking cancelBooking(Long bookingId, int numberOfSeatsToCancel) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new EntityNotFoundException("Booking not found"));
        Event event = booking.getEvent();
        LocalDateTime now = LocalDateTime.now();

        if (now.isAfter(event.getStartTime().minusHours(2))) {
            throw new ValidationException("Cancellation not allowed within 2 hours of event start");
        }

        int seatsToRelease = Math.min(numberOfSeatsToCancel, booking.getNumberOfSeats());
        BigDecimal refundAmount = calculateRefund(booking.getTotalPrice(), seatsToRelease, booking.getNumberOfSeats(), event.getStartTime(), now);

        event.setAvailableSeats(event.getAvailableSeats() + seatsToRelease);
        eventRepository.save(event);

        booking.setNumberOfSeats(booking.getNumberOfSeats() - seatsToRelease);
        booking.setTotalPrice(booking.getTotalPrice().subtract(refundAmount));
        bookingRepository.save(booking);

        return booking;
    }

    private BigDecimal calculateRefund(BigDecimal totalPrice, int seatsToCancel, int totalSeats, LocalDateTime eventStartTime, LocalDateTime now) {
        long hoursUntilEvent = Duration.between(now, eventStartTime).toHours();
        BigDecimal percentage;

        if (hoursUntilEvent < 2) {
            throw new ValidationException("Cancellation not allowed within 2 hours of event start");
        } else if (hoursUntilEvent < 12) {
            percentage = new BigDecimal("0.10");
        } else if (hoursUntilEvent < 24) {
            percentage = new BigDecimal("0.50");
        } else {
            percentage = new BigDecimal("0.80");
        }

        BigDecimal seatPrice = totalPrice.divide(new BigDecimal(totalSeats), RoundingMode.HALF_UP);
        return seatPrice.multiply(new BigDecimal(seatsToCancel)).multiply(percentage);
    }

    public Booking cancelBooking(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new EntityNotFoundException("Booking not found"));
        Event event = booking.getEvent();
        LocalDateTime now = LocalDateTime.now();

        if (now.isAfter(event.getStartTime().minusHours(2))) {
            throw new ValidationException("Cancellation not allowed within 2 hours of event start");
        }

        BigDecimal refundAmount = calculateRefund(booking.getTotalPrice(), event.getStartTime(), now);

        event.setAvailableSeats(event.getAvailableSeats() + booking.getNumberOfSeats());
        eventRepository.save(event);

        bookingRepository.delete(booking);

        booking.setTotalPrice(refundAmount);
        return booking;
    }

    private BigDecimal calculateRefund(BigDecimal totalPrice, LocalDateTime eventStartTime, LocalDateTime now) {
        long hoursUntilEvent = Duration.between(now, eventStartTime).toHours();
        BigDecimal percentage;

        if (hoursUntilEvent < 2) {
            throw new ValidationException("Cancellation not allowed within 2 hours of event start");
        } else if (hoursUntilEvent < 12) {
            percentage = new BigDecimal("0.10");
        } else if (hoursUntilEvent < 24) {
            percentage = new BigDecimal("0.50");
        } else {
            percentage = new BigDecimal("0.80");
        }

        return totalPrice.multiply(percentage);
    }
}
