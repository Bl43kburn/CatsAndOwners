package com.blackburn.services;

import com.blackburn.DTO.CatDTO;
import com.blackburn.DTO.CatOwnerDTO;
import com.blackburn.dao.CatDAO;
import com.blackburn.dao.CatOwnerDAO;
import com.blackburn.dao.TransferRequestRepository;
import com.blackburn.dao.UserRepository;
import com.blackburn.exceptions.SearchException;
import com.blackburn.exceptions.UserException;
import com.blackburn.mapping.CatMapper;
import com.blackburn.mapping.CatOwnerMapper;
import com.blackburn.model.Cat;
import com.blackburn.model.CatOwner;

import com.blackburn.model.Authority;
import com.blackburn.model.TransferRequest;
import com.blackburn.security.Role;
import io.github.perplexhub.rsql.RSQLJPASupport;
import io.micrometer.observation.Observation;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class StandardCatOwnerService implements CatOwnerService {

    private final CatOwnerDAO dao;

    private final CatService catService;

    private final TransferRequestRepository requestRepository;

    private final UserRepository userRepository;

    private final UserRegistrationService userRegistrationService;

    @Autowired
    public StandardCatOwnerService(CatOwnerDAO dao, UserRepository repository, UserRegistrationService userRegistrationService, CatService service, TransferRequestRepository requestRepository) {
        this.dao = dao;
        this.catService = service;
        this.userRepository = repository;
        this.userRegistrationService = userRegistrationService;
        this.requestRepository = requestRepository;
    }

    @Override
    public CatOwnerDTO create(String name, Date dateOfBirth, String login, String password) {
        userRegistrationService.addUser(login, password);
        CatOwner owner = new CatOwner(userRepository.findByLogin(login), name, dateOfBirth);
        dao.save(owner);
        return CatOwnerMapper.asDto(owner);
    }


    @Override
    @Transactional
    public void delete(UUID id) {
        CatOwner owner = dao.findById(id).orElseThrow();

        List<Cat> cats = owner.getCatPets().stream().toList();

        for (int i = 0; i < cats.size(); i++){
            catService.delete(cats.get(i).getId());
        }

        List<TransferRequest> requestsSenders = requestRepository.findBySender(owner);
        List<TransferRequest> requestsReceivers = requestRepository.findByReceiver(owner);
        requestRepository.deleteAll(requestsSenders);
        requestRepository.deleteAll(requestsReceivers);

        dao.delete(owner);
    }

    @Override
    public CatOwnerDTO findById(UUID id) {
        CatOwner owner = dao.findById(id).orElseThrow(() -> SearchException.CatOwnerNotFound());

        return CatOwnerMapper.asDto(owner);
    }

    @Override
    public List<CatDTO> getOwnedCats(UUID id) {
        CatOwner owner = dao.findById(id).orElseThrow(() -> SearchException.CatOwnerNotFound());
        return owner.getCatPets().stream().map(x -> CatMapper.asDTO(x)).toList();
    }

    @Override
    public List<CatOwnerDTO> getOwners() {
        return dao.findAll().stream().map(x -> CatOwnerMapper.asDto(x)).toList();
    }

    @Override
    public List<CatOwnerDTO> getByName(String name) {
        return dao.findByName(name).stream().map(x -> CatOwnerMapper.asDto(x)).toList();
    }

    @Override
    public List<CatOwnerDTO> getByDate(Date date) {
        return dao.findByDate(date).stream().map(x -> CatOwnerMapper.asDto(x)).toList();
    }

    @Override
    public List<CatOwnerDTO> RSQLQuerySearch(String query) {
        return dao.findAll(RSQLJPASupport.toSpecification(query)).stream().map(x -> CatOwnerMapper.asDto(x)).toList();
    }
}
