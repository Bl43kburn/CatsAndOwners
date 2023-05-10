package com.blackburn.services;

import com.blackburn.DTO.CatDTO;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public interface CatService {
    public CatDTO create(String name, Date dateOfBirth, String breed, String color, UUID ownerId);

    public void delete(UUID id);

    public CatDTO findById(UUID id);

    public List<CatDTO> getFriends(UUID id);

    public List<CatDTO> getCats();

    public void addCatFriend(UUID catId, UUID friendId);

    public void removeCatFriend(UUID catId, UUID friendId);

    public List<CatDTO> findByName(String name);

    public List<CatDTO> findByDate(Date date);

    public List<CatDTO> findByBreed(String breed);

    public List<CatDTO> findByColor(String color);

    public List<CatDTO> RSQLQuerySearch(String query);
}
