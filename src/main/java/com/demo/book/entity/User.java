package com.demo.book.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;


import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name="user_type",
        discriminatorType = DiscriminatorType.STRING)
@DiscriminatorValue("GENERIC")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "user_type", insertable = false,updatable = false)
    private String userType;

    @Email
    @NotBlank(message = "email is mandatory")
    private String email;

    @NotNull
    @Min(18)
    private int age;

    @NotBlank(message = "phone number is mandatory")
    @Column(name = "phone_number")
    private String phoneNumber;

    private String address;

    @NotBlank(message = "name is mandatory")
    @Column(name = "full_name")
    private String fullName;

    @Column(name = "created_date")
    private LocalDateTime createdDate = LocalDateTime.now();
    public User() {

    }
}

