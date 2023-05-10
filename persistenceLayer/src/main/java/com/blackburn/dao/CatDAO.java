package com.blackburn.dao;

import com.blackburn.model.Cat;
import com.blackburn.model.CatOwner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CatDAO extends JpaRepository<Cat, UUID>, JpaSpecificationExecutor<Cat>, CatDaoFragment {
}
