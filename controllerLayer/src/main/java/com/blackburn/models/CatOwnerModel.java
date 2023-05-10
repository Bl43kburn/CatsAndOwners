package com.blackburn.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;

@AllArgsConstructor
public class CatOwnerModel {

    @Getter
    private String name;

    @Getter
    private Date dateOfBirth;

    @Getter
    private String password;

    @Getter
    private String login;
}
