package com.blackburn.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
public class Color {
    @Id
    @Getter
    @Setter
    private String color;


    public Color(String color) {
        this.color = color;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Color color1 = (Color) o;

        return color.equals(color1.color);
    }

    @Override
    public int hashCode() {
        return color.hashCode();
    }

    protected Color() {
    }
}
