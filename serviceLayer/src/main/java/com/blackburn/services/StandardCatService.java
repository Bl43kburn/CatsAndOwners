package com.blackburn.services;

import com.blackburn.DTO.CatDTO;
import com.blackburn.dao.ColorDAO;
import com.blackburn.dao.TransferRequestRepository;
import com.blackburn.exceptions.*;
import com.blackburn.mapping.CatMapper;
import com.blackburn.model.Cat;
import com.blackburn.model.CatOwner;
import com.blackburn.model.Color;
import com.blackburn.dao.CatDAO;
import com.blackburn.dao.CatOwnerDAO;
import com.blackburn.model.TransferRequest;
import com.blackburn.security.FilterChain;
import com.blackburn.security.catOwnershipFilter.CatOwnershipDefaultFilters;
import io.github.perplexhub.rsql.RSQLJPASupport;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class StandardCatService implements CatService {
    private final CatDAO dao;

    private final CatOwnerDAO ownerDAO;

    private final ColorDAO colorDAO;

    private final TransferRequestRepository transferRequestRepository;

    @Autowired
    public StandardCatService(CatDAO dao, CatOwnerDAO ownerDAO, ColorDAO colorDAO, TransferRequestRepository requestRepository) {
        this.dao = dao;
        this.ownerDAO = ownerDAO;
        this.colorDAO = colorDAO;
        this.transferRequestRepository = requestRepository;
    }

    @Override
    @Transactional
    public CatDTO create(String name, Date dateOfBirth, String breed, String color, UUID ownerId) {

        if (name.isBlank() || breed.isBlank() || color.isBlank())
            throw  EntityException.InitializationError();

        CatOwner owner = ownerDAO.findById(ownerId).orElseThrow(() -> SearchException.CatOwnerNotFound());

        if (colorDAO.findById(color).isEmpty())
            throw ColorException.UnsupportedColorException();

        Cat cat = new Cat(name, dateOfBirth, breed, new Color(color), owner);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (!CatOwnershipDefaultFilters.getDefaultFilter().process(cat, (UserDetails) authentication.getPrincipal()))
            throw OwnershipException.NotOwnedEntity();

        dao.save(cat);

        owner.getCatPets().add(cat);
        ownerDAO.save(owner);

        return CatMapper.asDTO(cat);
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        Cat cat = dao.findById(id).orElseThrow(() -> SearchException.CatNotFound());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();


        if (!CatOwnershipDefaultFilters.getDefaultFilter().process(cat, (UserDetails) authentication.getPrincipal()))
            throw OwnershipException.NotOwnedEntity();

        CatOwner catOwner = cat.getOwner();

        for (Cat friend : cat.getFriends()) {
            friend.getFriends().remove(cat);
            dao.save(friend);
        }

        List<TransferRequest> requests = transferRequestRepository.findByCat(cat);
        transferRequestRepository.deleteAll(requests);

        catOwner.getCatPets().remove(cat);
        ownerDAO.save(catOwner);

        dao.delete(cat);
    }

    @Override
    @Transactional
    public CatDTO findById(UUID id) {
        Cat cat = dao.findById(id).orElseThrow(() -> SearchException.CatNotFound());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (!CatOwnershipDefaultFilters.getDefaultFilter().process(cat, (UserDetails) authentication.getPrincipal()))
            throw OwnershipException.NotOwnedEntity();

        return CatMapper.asDTO(cat);
    }

    @Override
    @Transactional
    public List<CatDTO> getFriends(UUID id) {
        Cat cat = dao.findById(id).orElseThrow(() -> SearchException.CatNotFound());
        return cat.getFriends().stream().map(x -> CatMapper.asDTO(x)).toList();
    }

    @Override
    @Transactional
    public List<CatDTO> getCats() {
        FilterChain<Cat> filterChain = CatOwnershipDefaultFilters.getDefaultFilter();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        return dao.findAll().stream().filter(x -> filterChain.process(x, (UserDetails) authentication.getPrincipal())).map(x -> CatMapper.asDTO(x)).toList();
    }

    @Override
    @Transactional
    public void addCatFriend(UUID catId, UUID friendId) {

        if (friendId == catId)
            throw FriendshipException.BefriendsItself();

        Cat cat = dao.findById(catId).orElseThrow(() -> SearchException.CatNotFound());

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (!CatOwnershipDefaultFilters.getDefaultFilter().process(cat, (UserDetails) authentication.getPrincipal()))
            throw OwnershipException.NotOwnedEntity();


        Cat friend = dao.findById(friendId).orElseThrow();

        if (friend.getFriends().contains(cat))
            throw FriendshipException.AlreadyFriends();

        cat.getFriends().add(friend);
        friend.getFriends().add(cat);

        dao.save(cat);
        dao.save(friend);
    }

    @Override
    @Transactional
    public void removeCatFriend(UUID catId, UUID friendId) {
        Cat cat = dao.findById(catId).orElseThrow();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();


        if (!CatOwnershipDefaultFilters.getDefaultFilter().process(cat, (UserDetails) authentication.getPrincipal()))
            throw OwnershipException.NotOwnedEntity();

        Cat friend = dao.findById(friendId).orElseThrow(() -> SearchException.CatNotFound());

        if (!friend.getFriends().contains(cat))
            throw FriendshipException.AreNotFriends();

        cat.getFriends().remove(friend);
        friend.getFriends().remove(cat);

        dao.save(cat);
        dao.save(friend);
    }


    @Override
    public List<CatDTO> findByName(String name) {
        FilterChain<Cat> filterChain = CatOwnershipDefaultFilters.getDefaultFilter();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        return dao.findByName(name).stream().filter(x -> filterChain.process(x, (UserDetails) authentication.getPrincipal())).map(x -> CatMapper.asDTO(x)).toList();
    }

    @Override
    public List<CatDTO> findByDate(Date date) {
        FilterChain<Cat> filterChain = CatOwnershipDefaultFilters.getDefaultFilter();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        return dao.findByDate(date).stream().filter(x -> filterChain.process(x, (UserDetails) authentication.getPrincipal())).map(x -> CatMapper.asDTO(x)).toList();
    }

    @Override
    public List<CatDTO> findByBreed(String breed) {
        FilterChain<Cat> filterChain = CatOwnershipDefaultFilters.getDefaultFilter();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        return dao.findByBreed(breed).stream().filter(x -> filterChain.process(x, (UserDetails) authentication.getPrincipal())).map(x -> CatMapper.asDTO(x)).toList();
    }

    @Override
    public List<CatDTO> findByColor(String color) {
        FilterChain<Cat> filterChain = CatOwnershipDefaultFilters.getDefaultFilter();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        return dao.findByColor(color).stream().filter(x -> filterChain.process(x, (UserDetails) authentication.getPrincipal())).map(x -> CatMapper.asDTO(x)).toList();

    }

    @Override
    public List<CatDTO> RSQLQuerySearch(String query) {
        FilterChain<Cat> filterChain = CatOwnershipDefaultFilters.getDefaultFilter();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        return dao.findAll(RSQLJPASupport.toSpecification(query)).stream().filter(x -> filterChain.process(x, (UserDetails) authentication.getPrincipal())).map(x -> CatMapper.asDTO(x)).toList();
    }
}
