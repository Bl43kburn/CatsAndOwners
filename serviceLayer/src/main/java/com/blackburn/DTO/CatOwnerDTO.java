package com.blackburn.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
public class CatOwnerDTO {
    @Getter
    private String name;

    @Getter
    private Date dateOfBirth;

    @Getter
    private List<CatDTO> cats;

    @Getter
    private UUID id;

    @Getter
    private String username;
}
