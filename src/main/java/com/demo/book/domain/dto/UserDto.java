package com.demo.book.domain.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDto {
    private long id;
    private String userType;
    private int age;
    private String email;
    private String address;
    private String fullName;
    private String phoneNumber;
    public static class Builder {
        private long id;
        private String userType;
        private int age;
        private String email;
        private String address;
        private String phoneNumber;
        private String fullName;
        public Builder id(long id) {
            this.id = id;
            return this;
        }
        public Builder fullName(String fullName) {
            this.fullName = fullName;
            return this;
        }
        public Builder phoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
            return this;
        }
        public Builder userType(String userType) {
            this.userType = userType;
            return this;
        }
        public Builder age(int age) {
            this.age = age;
            return this;
        }
        public Builder email(String email) {
            this.email = email;
            return this;
        }
        public Builder address(String address) {
            this.address = address;
            return this;
        }

        public UserDto build() {
            return new UserDto(this);
        }
    }
    public UserDto(long id, String userType, int age) {
        this.id = id;
        this.userType = userType;
        this.age = age;
    }

    public UserDto(long id, String userType, int age, String email, String address, String fullName) {
        this.id = id;
        this.userType = userType;
        this.age = age;
        this.email = email;
        this.address = address;
        this.fullName = fullName;
    }

    public UserDto(Builder builder){
        this.id = builder.id;
        this.userType = builder.userType;
        this.age = builder.age;
        this.email = builder.email;
        this.address = builder.address;
        this.phoneNumber = builder.phoneNumber;
        this.fullName = builder.fullName;
    }
}
