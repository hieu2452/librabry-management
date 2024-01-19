package com.demo.book.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Entity
@DiscriminatorValue("ADMIN")
@Data
public class Admin extends User{
    private String username;
    private String password;

    public Admin() {
        super();
    }

}
