package com.blackburn.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;
import java.util.UUID;

@AllArgsConstructor
public class CatModel {

    @Getter
    private String name;

    @Getter
    private Date dateOfBirth;

    @Getter
    private String breed;

    @Getter
    private String color;

    @Getter
    private UUID ownerId;
}
