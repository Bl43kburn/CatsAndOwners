package com.blackburn.services;

import com.blackburn.DTO.CatDTO;
import com.blackburn.DTO.CatOwnerDTO;
import com.blackburn.model.CatOwner;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public interface CatOwnerService {
    public CatOwnerDTO create(String name, Date dateOfBirth, String login, String password);

    public void delete(UUID id);

    public CatOwnerDTO findById(UUID id);

    public List<CatDTO> getOwnedCats(UUID id);

    public List<CatOwnerDTO> getOwners();

    public List<CatOwnerDTO> getByName(String name);

    public List<CatOwnerDTO> getByDate(Date date);

    public List<CatOwnerDTO> RSQLQuerySearch(String query);
}
