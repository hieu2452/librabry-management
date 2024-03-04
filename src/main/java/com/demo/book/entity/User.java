package com.demo.book.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;
import javax.validation.constraints.Email;

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
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "user_type", insertable = false,updatable = false)
    private String userType;
    @Column(name = "display_name")
    private String displayName;
    @Email
    private String email;
    private int age;
    @Column(name = "phone_number")
    private String phoneNumber;
    private String address;
    @Column(name = "full_name")
    private String fullName;
    @Column(name = "created_date")
    private LocalDateTime createdDate = LocalDateTime.now();
    public User() {

    }
}

