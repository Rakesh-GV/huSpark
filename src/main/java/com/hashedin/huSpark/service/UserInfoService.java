package com.hashedin.huSpark.service;

import com.hashedin.huSpark.entity.User;
import com.hashedin.huSpark.model.Mapper;
import com.hashedin.huSpark.model.RegisterUser;
import com.hashedin.huSpark.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserInfoService implements UserDetailsService {

    private final UserRepository repository;

    private final Mapper mapper;

    private final PasswordEncoder encoder;

    public UserInfoService(UserRepository repository, PasswordEncoder encoder, Mapper mapper) {
        this.repository = repository;
        this.encoder = encoder;
        this.mapper = mapper;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userDetail = repository.findByEmail(username);
        // Converting userDetail to UserDetails
        return userDetail.map(UserInfoDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("User not found " + username));
    }

    public String addUser(RegisterUser registerUser) {
        var user = mapper.MapToUser(registerUser);
        user.setPassword(encoder.encode(user.getPassword()));
        repository.save(user);
        return "User Added Successfully";
    }

    public User findByEmail(String email) {
        return repository.findByEmail(email).
                orElseThrow(() -> new EntityNotFoundException("user not found"));

    }
}
