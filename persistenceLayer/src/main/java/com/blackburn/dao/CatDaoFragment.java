package com.blackburn.dao;

import com.blackburn.model.Cat;

import java.util.Date;
import java.util.List;

public interface CatDaoFragment {

    public List<Cat> findByName(String name);

    public List<Cat> findByDate(Date date);

    public List<Cat> findByBreed(String breed);

    public List<Cat> findByColor(String color);

}
