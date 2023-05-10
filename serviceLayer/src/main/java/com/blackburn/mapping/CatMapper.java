package com.blackburn.mapping;

import com.blackburn.DTO.CatDTO;
import com.blackburn.model.Cat;

public class CatMapper {
    public static CatDTO asDTO(Cat cat) {
        return new CatDTO(cat.getName(), cat.getColor().getColor(), cat.getBreed(), cat.getDateOfBirth(), cat.getOwner().getId(), cat.getOwner().getUsername(), cat.getFriends().size(), cat.getId());
    }


}
