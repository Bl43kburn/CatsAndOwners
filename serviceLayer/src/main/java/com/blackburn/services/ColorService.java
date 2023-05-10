package com.blackburn.services;

import com.blackburn.DTO.ColorDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ColorService {
    public ColorDTO create(String color);

    public ColorDTO findColor(String color);

    public List<ColorDTO> getColors();
}
