package com.blackburn.dao;

import com.blackburn.model.Color;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;


@Repository
public interface ColorDAO extends JpaRepository<Color, String> {
}
