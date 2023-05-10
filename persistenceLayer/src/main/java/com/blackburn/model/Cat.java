package com.blackburn.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

@Entity
public class Cat {

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private Date dateOfBirth;

    @Getter
    @Setter
    private String breed;

    @Getter
    @Setter
    @ManyToOne
    private Color color;

    @Getter
    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", nullable = false)
    private CatOwner owner;

    @Getter
    @Setter
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "cat_friend",
            joinColumns = @JoinColumn(name = "cat_id"),
            inverseJoinColumns = @JoinColumn(name = "friend_cat_id")
    )
    private Set<Cat> friends;

    @Id
    @Getter
    @Setter
    @Column(name = "cat_id")
    private UUID id;

    public Cat(String name, Date dob, String breed, Color color, CatOwner owner) {
        this.name = name;
        this.dateOfBirth = dob;
        this.breed = breed;
        this.color = color;
        this.owner = owner;
        this.id = UUID.randomUUID();
        friends = new HashSet<>();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cat cat = (Cat) o;
        return id.equals(cat.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    protected Cat() {
    }
}
