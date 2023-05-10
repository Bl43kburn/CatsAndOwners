package com.blackburn.controllers;

import com.blackburn.DTO.CatDTO;
import com.blackburn.exceptions.OwnershipException;
import com.blackburn.models.CatModel;
import com.blackburn.models.DateNullifier;
import com.blackburn.services.CatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.access.prepost.PreFilter;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RequestMapping("cat")
@RestController
public class CatController {

    private final CatService service;

    @Autowired
    public CatController(@Qualifier("standardCatService") CatService service) {
        this.service = service;
    }

    @PostMapping("/create")
    public CatDTO createCat(@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestBody CatModel model) {
        Date newDate = DateNullifier.NullifyTime(model.getDateOfBirth());
        return service.create(model.getName(), newDate, model.getBreed(), model.getColor(), model.getOwnerId());
    }

    @DeleteMapping("/delete")
    public void deleteCat(@RequestParam UUID catId) {
        service.delete(catId);
    }

    @PostMapping("/addFriend/{catId}/{friendId}")
    public void addFriend(@PathVariable UUID catId, @PathVariable UUID friendId) {
        service.addCatFriend(catId, friendId);
    }

    @DeleteMapping("/deleteFriend/{catId}/{friendId}")
    public void deleteFriend(@PathVariable UUID catId, @PathVariable UUID friendId) {
        service.removeCatFriend(catId, friendId);
    }

    @GetMapping("/{catId}/friends")
    public List<CatDTO> getFriends(@PathVariable UUID catId) {
        return service.getFriends(catId);
    }

    @GetMapping("/getAll")
    public List<CatDTO> getCats() {
        return service.getCats();
    }

    @GetMapping("/name/{name}")
    public List<CatDTO> getCatsByName(@PathVariable("name") String name) {
        return service.findByName(name);
    }

    @GetMapping("/date/{date}")
    public List<CatDTO> getCatsByDate(@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @PathVariable("date") Date date) {
        Date newDate = DateNullifier.NullifyTime(date);
        return service.findByDate(newDate);
    }

    @GetMapping("/breed/{breed}")
    public List<CatDTO> getCatsByBreed(@PathVariable("breed") String breed) {
        return service.findByBreed(breed);
    }

    @GetMapping("/color/{color}")
    public List<CatDTO> getCatsByColor(@PathVariable("color") String color) {
        return service.findByColor(color);
    }

    @GetMapping("/query")
    public List<CatDTO> getCatsByQuery(@RequestParam String query) {
        return service.RSQLQuerySearch(query);
    }

}
