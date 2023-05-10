package com.blackburn.controllers;

import com.blackburn.DTO.CatDTO;
import com.blackburn.DTO.CatOwnerDTO;
import com.blackburn.models.CatOwnerModel;
import com.blackburn.models.DateNullifier;
import com.blackburn.services.CatOwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.access.prepost.PreFilter;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("catOwner")
public class CatOwnerController {
    private final CatOwnerService service;

    @Autowired
    public CatOwnerController(@Qualifier("standardCatOwnerService") CatOwnerService service) {
        this.service = service;
    }


    @PostMapping(value = "/create")
    @PreAuthorize("hasAuthority('root')")
    public CatOwnerDTO createOwner(@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestBody CatOwnerModel model) {
        Date newDate = DateNullifier.NullifyTime(model.getDateOfBirth());
        return service.create(model.getName(), newDate, model.getLogin(), model.getPassword());
    }

    @PreAuthorize("hasAuthority('root')")
    @DeleteMapping("/delete")
    public void deleteOwner(@RequestParam UUID ownerId) {
        service.delete(ownerId);
    }

    @PreAuthorize("hasAuthority('root')")
    @GetMapping("/getOwner/{ownerId}")
    public List<CatDTO> getOwnedCats(@PathVariable UUID ownerId) {
        return service.getOwnedCats(ownerId);
    }


    @PreAuthorize("hasAuthority('root')")
    @GetMapping("/getAll")
    public List<CatOwnerDTO> getAll() {
        return service.getOwners();
    }

    @PreAuthorize("hasAuthority('root')")
    @GetMapping("/name/{name}")
    public List<CatOwnerDTO> getByName(@PathVariable String name) {
        return service.getByName(name);
    }

    @PreAuthorize("hasAuthority('root')")
    @GetMapping("/date/{date}")
    public List<CatOwnerDTO> getOwnerByDate(@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @PathVariable Date date) {
        Date newDate = DateNullifier.NullifyTime(date);
        return service.getByDate(newDate);
    }

    @PreAuthorize("hasAuthority('root')")
    @GetMapping("/query")
    public List<CatOwnerDTO> getOwnersByQuery(@RequestParam String query) {
        return service.RSQLQuerySearch(query);
    }
}
