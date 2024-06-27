package com.hashedin.huSpark.configuration;

import com.hashedin.huSpark.entity.EventType;
import com.hashedin.huSpark.entity.Theatre;
import com.hashedin.huSpark.entity.User;
import com.hashedin.huSpark.repository.EventTypeRepository;
import com.hashedin.huSpark.repository.TheaterRepository;
import com.hashedin.huSpark.repository.UserRepository;
import com.hashedin.huSpark.service.UserInfoDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Component
public class SetupDataLoader implements
        ApplicationListener<ContextRefreshedEvent> {

    boolean alreadySetup = false;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EventTypeRepository eventTypeRepository;

    @Autowired
    private TheaterRepository theaterRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (alreadySetup)
            return;

        seedAccounts();
        seedEventTypes();
        seedTheatres();

        alreadySetup = true;
    }

    private void seedEventTypes() {
        final String eventTypes = "Music, Movie, StandupComedy";

        for (String eventType : eventTypes.split(",")) {
            eventType = eventType.trim();
            if (eventTypeRepository.findByName(eventType).isEmpty()) {
                var eveType = new EventType();
                eveType.setName(eventType);
                eventTypeRepository.save(eveType);
            }
        }
    }

    private void seedAccounts() {
        final String adminEmail = "admin@gmail.com";
        final String userEmail = "user@gmail.com";

        if (userRepository.findByEmail(adminEmail).isEmpty()) {
            var admin = new User();
            admin.setName("Admin");
            admin.setPassword(passwordEncoder.encode("test"));
            admin.setAddress("Address 1");
            admin.setMobile("1234567890");
            admin.setEmail("admin@gmail.com");
            admin.setRoles(UserInfoDetails.ROLE_ADMIN);
            admin.setDob(LocalDate.of(2000, 1, 1));
            userRepository.save(admin);
        }

        if (userRepository.findByEmail(userEmail).isEmpty()) {
            var user = new User();
            user.setName("User");
            user.setPassword(passwordEncoder.encode("test"));
            user.setAddress("Address 1");
            user.setMobile("1234567890");
            user.setEmail("user@gmail.com");
            user.setRoles(UserInfoDetails.ROLE_USER);
            user.setDob(LocalDate.of(2000, 1, 1));
            userRepository.save(user);
        }
    }

    private void seedTheatres() {
        final String theatreNames = "IMax, PVR";

        for (String theatreName : theatreNames.split(",")) {
            theatreName = theatreName.trim();
            if (theaterRepository.findByName(theatreName).isEmpty()) {
                var theatre = new Theatre();
                theatre.setName(theatreName);
                theatre.setLocation("Bengaluru");
                theaterRepository.save(theatre);
            }
        }
    }
}
