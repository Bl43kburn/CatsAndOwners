package com.blackburn.mapping;

import com.blackburn.DTO.ColorDTO;
import com.blackburn.model.Color;

public class ColorMapper {
    public static ColorDTO asDto(Color color) {
        return new ColorDTO(color.getColor());
    }
}
