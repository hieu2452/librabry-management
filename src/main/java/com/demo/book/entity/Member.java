package com.demo.book.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("MEMBER")
public class Member extends User{
    public Member() {

    }
}
