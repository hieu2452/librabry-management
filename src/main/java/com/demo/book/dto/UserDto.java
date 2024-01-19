package com.demo.book.dto;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
public class UserDto {
    private long id;
    private String userType;
    private String displayName;
    private int age;

    public UserDto(long id, String userType, String displayName, int age) {
        this.id = id;
        this.userType = userType;
        this.displayName = displayName;
        this.age = age;
    }
}
