package com.blackburn.services;

import com.blackburn.DTO.ColorDTO;
import com.blackburn.dao.ColorDAO;
import com.blackburn.exceptions.ColorException;
import com.blackburn.exceptions.EntityException;
import com.blackburn.exceptions.SearchException;
import com.blackburn.mapping.ColorMapper;
import com.blackburn.model.Color;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StandardColorService implements ColorService {

    private final ColorDAO dao;

    @Autowired
    public StandardColorService(ColorDAO dao) {
        this.dao = dao;
    }


    @Override
    public ColorDTO create(String color) {
        if (color.isBlank())
            throw EntityException.InitializationError();

        Color col = new Color(color);

        if (dao.findById(color).isPresent())
            throw ColorException.DuplicateColorAddition();

        dao.save(col);
        return ColorMapper.asDto(col);
    }

    @Override
    public ColorDTO findColor(String color) {
        Optional<Color> col = dao.findById(color);
        return ColorMapper.asDto(col.orElseThrow(() -> SearchException.ColorNotFound()));
    }

    @Override
    public List<ColorDTO> getColors() {
        return dao.findAll().stream().map(x -> ColorMapper.asDto(x)).toList();
    }
}
