package com.blackburn.dao;

import com.blackburn.model.Authority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<Authority, UUID> {
    public Authority findByLogin(String login);
}
