package com.hashedin.huSpark.model;

import com.hashedin.huSpark.entity.Event;
import com.hashedin.huSpark.entity.User;
import com.hashedin.huSpark.repository.EventTypeRepository;
import com.hashedin.huSpark.repository.TheaterRepository;
import com.hashedin.huSpark.service.UserInfoDetails;
import org.springframework.beans.InvalidPropertyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Mapper {

    @Autowired
    private EventTypeRepository eventTypeRepository;

    @Autowired
    private TheaterRepository theatreRepository;

    public User MapToUser(RegisterUser registerUser){
        var user = new User();
        user.setName(registerUser.getName());
        user.setEmail(registerUser.getEmail());
        user.setMobile(registerUser.getMobile());
        user.setDob(registerUser.getDob());
        user.setRoles(UserInfoDetails.ROLE_USER);

        return user;
    }

    public Event MapToEvent(CreateEvent createEvent){
        var event = new Event();
        event.setName(createEvent.getName());
        event.setAvailableSeats(createEvent.getAvailableSeats());
        event.setEndTime(createEvent.getEndTime());
        event.setPrice(createEvent.getPrice());
        event.setMaxOccupancy(createEvent.getMaxOccupancy());
        event.setStartTime(createEvent.getStartTime());

        var eventType = eventTypeRepository.findById(createEvent.getEventTypeId());
        if(eventType.isEmpty())
            throw new InvalidPropertyException(createEvent.getClass(), "eventTypeId", "Event type not found");
        event.setEventType(eventType.get());

        var theatre = theatreRepository.findById(createEvent.getTheatreId());
        if(theatre.isEmpty())
            throw new InvalidPropertyException(createEvent.getClass(), "theatreId", "Theatre not found");
        event.setTheatre(theatre.get());

        return event;
    }
}
