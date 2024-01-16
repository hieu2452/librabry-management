package com.demo.book.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;


import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String username;
    @JsonIgnore
    private String password;
    private String displayName;
    private int age;

    public User() {

    }
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "role_id",referencedColumnName = "id"))
    private List<Role> roles = new ArrayList<>();

    public static class Builder {
        private final String username;
        private final String password;
        private String displayName;
        private int age;
        public Builder(String username,String password) {
            this.username = username;
            this.password = password;
        }

        public Builder displayName(String displayName) {
            this.displayName = displayName;
            return this;
        }
        public Builder description(int age) {
            this.age = age;
            return this;
        }
        public User build() {
            return new User(this);
        }

    }

    private User(Builder builder) {
        this.username = builder.username;
        this.password = builder.password;
        this.displayName = builder.displayName;
        this.age = builder.age;
    }

    public long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getDisplayName() {
        return displayName;
    }

    public int getAge() {
        return age;
    }

    public List<Role> getRoles() {
        return roles;
    }
}

