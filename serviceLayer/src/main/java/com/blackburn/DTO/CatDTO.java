package com.blackburn.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;
import java.util.UUID;


@AllArgsConstructor
public class CatDTO {

    @Getter
    private String name;

    @Getter
    private String color;

    @Getter
    private String breed;

    @Getter
    private Date dateOfBirth;

    @Getter
    private UUID ownerId;

    @Getter
    private String ownerUsername;

    @Getter
    private int friendCount;

    @Getter
    private UUID id;

}
