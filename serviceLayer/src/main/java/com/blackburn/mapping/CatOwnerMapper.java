package com.blackburn.mapping;

import com.blackburn.DTO.CatOwnerDTO;
import com.blackburn.model.CatOwner;

public class CatOwnerMapper {
    public static CatOwnerDTO asDto(CatOwner catOwner) {
        return new CatOwnerDTO(catOwner.getName(), catOwner.getDateOfBirth(), catOwner.getCatPets().stream().map(x -> CatMapper.asDTO(x)).toList(), catOwner.getId(), catOwner.getUsername());
    }
}
