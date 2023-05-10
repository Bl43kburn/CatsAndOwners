package com.blackburn.controllers;

import com.blackburn.DTO.ColorDTO;
import com.blackburn.services.ColorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("color")
public class ColorController {

    private final ColorService service;

    @Autowired
    public ColorController(@Qualifier("standardColorService") ColorService service) {
        this.service = service;
    }

    @PreAuthorize("hasAuthority('root')")
    @PostMapping("/create/{color}")
    public ColorDTO createColor(@RequestBody String color) {
        return service.create(color);
    }


    @PreAuthorize("hasAuthority('root')")
    @GetMapping("/find/{color}")
    public ColorDTO findColor(@PathVariable String color) {
        return service.findColor(color);
    }

    @PreAuthorize("hasAuthority('root')")
    @GetMapping("/getAll")
    public List<ColorDTO> getAll() {
        return service.getColors();
    }
}
