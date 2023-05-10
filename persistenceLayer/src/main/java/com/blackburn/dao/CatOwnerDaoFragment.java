package com.blackburn.dao;

import com.blackburn.model.Cat;
import com.blackburn.model.CatOwner;

import java.util.Date;
import java.util.List;

public interface CatOwnerDaoFragment {
    public List<CatOwner> findByName(String name);

    public List<CatOwner> findByDate(Date date);
}
