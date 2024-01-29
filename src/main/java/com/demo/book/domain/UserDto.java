package com.demo.book.domain;

import lombok.Data;

@Data
public class UserDto {
    private long id;
    private String userType;
    private String displayName;
    private int age;
    private String email;
    private String address;
    private String fullName;
    public static class Builder {
        private long id;
        private String userType;
        private String displayName;
        private int age;
        private String email;
        private String address;
        private String fullName;
        public Builder id(long id) {
            this.id = id;
            return this;
        }
        public Builder fullName(String fullName) {
            this.fullName = fullName;
            return this;
        }
        public Builder userType(String userType) {
            this.userType = userType;
            return this;
        }
        public Builder displayName(String displayName) {
            this.displayName = displayName;
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
    public UserDto(long id, String userType, String displayName, int age) {
        this.id = id;
        this.userType = userType;
        this.displayName = displayName;
        this.age = age;
    }

    public UserDto(long id, String userType, String displayName, int age, String email, String address, String fullName) {
        this.id = id;
        this.userType = userType;
        this.displayName = displayName;
        this.age = age;
        this.email = email;
        this.address = address;
        this.fullName = fullName;
    }

    public UserDto(Builder builder){
        this.id = builder.id;
        this.userType = builder.userType;
        this.displayName = builder.displayName;
        this.age = builder.age;
        this.email = builder.email;
        this.address = builder.address;
        this.fullName = builder.fullName;
    }
}
