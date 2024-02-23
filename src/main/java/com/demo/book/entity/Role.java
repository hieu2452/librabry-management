package com.demo.book.entity;

import javax.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "roles")
@Data
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String role;
    public Role() {

    }
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "role_permission", joinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "permission_id",referencedColumnName = "id"))
    private List<Permission> permissions = new ArrayList<>();

    public static class Builder {
        private final String role;
        public Builder(String role) {
            this.role = role;
        }
        public Role build() {
            return new Role(this);
        }

    }

    private Role(Builder builder) {
        this.role = builder.role;
    }

}
