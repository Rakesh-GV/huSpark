package com.hashedin.huSpark.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.Period;

@Entity
@Table(name = "users_bookMyShow")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name is mandatory")
    private String name;

    @NotBlank(message = "Mobile number is mandatory")
    @Size(min = 10, max = 10, message = "Mobile number must be 10 digits")
    @Pattern(regexp = "^\\d{10}$")
    private String mobile;

    @NotBlank(message = "Email is mandatory")
    @Email(message = "Email should be valid")
    @Pattern(regexp = "^([a-zA-Z0-9_+-.]+)@gmail\\.[a-zA-Z0-9-.]+$")
    private String email;

    @NotBlank(message = "Address is mandatory")
    private String address;

    @NotBlank(message = "Password is mandatory")
    @Size(min = 8)
    private String password;

    @Past(message = "DOB should be in the past")
    private LocalDate dob;

    @Transient
    public int getAge() {
        return Period.between(this.dob, LocalDate.now()).getYears();
    }

    private String roles;


}
