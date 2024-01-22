package com.demo.book.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Entity
@DiscriminatorValue("MEMBER")
@Data
public class Member extends User{

    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "libraryCard_id", referencedColumnName = "id")
    private LibraryCard libraryCard;
    public Member() {

    }
}
