package com.demo.book.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "permission")
@Data
public class Permission {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;
    private String permission;
    public Permission() {

    }

    public static class Builder {
        private final String permission;
        public Builder(String permission) {
            this.permission = permission;
        }
        public Permission build() {
            return new Permission(this);
        }

    }

    private Permission(Builder builder) {
        this.permission = builder.permission;
    }

}
