package com.blackburn.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
public class CatOwner {

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private Date dateOfBirth;

    @Getter
    @Setter
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "owner")
    private Set<Cat> catPets;

    @Id
    @Getter
    @Setter
    private UUID id;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Authority authority;

    public CatOwner(Authority authority, String name, Date dateOfBirth) {
        this.name = name;
        this.authority = authority;
        this.dateOfBirth = dateOfBirth;
        this.id = UUID.randomUUID();
        this.catPets = new HashSet<>();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CatOwner catOwner = (CatOwner) o;

        return id.equals(catOwner.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    public String getUsername() {
        return authority.getLogin();
    }

    protected CatOwner() {
    }
}
