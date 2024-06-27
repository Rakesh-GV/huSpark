package com.hashedin.huSpark.service;

import com.hashedin.huSpark.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class UserInfoDetails implements UserDetails {
    public final static String ROLE_ADMIN = "ROLE_ADMIN";
    public final static String ROLE_USER = "ROLE_USER";

    private String name;
    private String password;
    private final Collection<? extends GrantedAuthority> authorities;
    public UserInfoDetails(User userInfo) {
        name = userInfo.getEmail();
        password = userInfo.getPassword();
        authorities = getAuthorities(userInfo.getRoles());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    private Collection<? extends GrantedAuthority> getAuthorities(String roles) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (String role : roles.split(",")) {
            authorities.add(new SimpleGrantedAuthority(role));
        }
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }
    @Override
    public String getUsername() {
        return name;
    }
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    @Override
    public boolean isEnabled() {
        return true;
    }

}
