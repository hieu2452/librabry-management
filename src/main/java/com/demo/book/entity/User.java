package com.demo.book.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.persistence.*;


import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="user_type",
        discriminatorType = DiscriminatorType.STRING)
@DiscriminatorValue("GENERIC")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "user_type", insertable = false,updatable = false)
    private String userType;
    private String displayName;
    private int age;
    public User() {

    }

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "role_id",referencedColumnName = "id"))
    private List<Role> roles = new ArrayList<>();
    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private List<Bill> bills = new ArrayList<>();
    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "library_id", referencedColumnName = "id")
    private LibraryCard libraryCard;

    public long getId() {
        return id;
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

    public List<Bill> getBills() {
        return bills;
    }
    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public void setBills(List<Bill> bills) {
        this.bills = bills;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public LibraryCard getLibraryCard() {
        return libraryCard;
    }

    public void setLibraryCard(LibraryCard libraryCard) {
        this.libraryCard = libraryCard;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

}

