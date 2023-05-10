package com.blackburn.services;

import com.blackburn.DTO.TransferRequestDTO;
import com.blackburn.dao.CatDAO;
import com.blackburn.dao.CatOwnerDAO;
import com.blackburn.dao.TransferRequestRepository;
import com.blackburn.exceptions.FriendshipException;
import com.blackburn.exceptions.OwnershipException;
import com.blackburn.exceptions.SearchException;
import com.blackburn.mapping.TransferRequestMapper;
import com.blackburn.model.Cat;
import com.blackburn.model.CatOwner;
import com.blackburn.model.TransferRequest;
import com.blackburn.security.catOwnershipFilter.CatOwnershipDefaultFilters;
import com.blackburn.security.transferRequestFilters.TransferRequestDefaultFilters;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class StandardCatTransferService implements CatTransferService {

    private final CatOwnerDAO ownerDAO;

    private final CatDAO catDAO;

    private final TransferRequestRepository transferRequestRepository;

    @Autowired
    public StandardCatTransferService(CatOwnerDAO catOwnerDAO, CatDAO catDAO, TransferRequestRepository transferRequestRepository) {
        this.ownerDAO = catOwnerDAO;
        this.catDAO = catDAO;
        this.transferRequestRepository = transferRequestRepository;
    }

    @Override
    @Transactional
    public void acceptRequest(UUID requestID) {
        TransferRequest transferRequest = transferRequestRepository.findById(requestID).orElseThrow(() -> SearchException.RequestNotFound());

        Cat cat = transferRequest.getCat();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();


        if (!TransferRequestDefaultFilters.getAbleToAcceptFilter().process(transferRequest, (UserDetails) authentication.getPrincipal()))
            throw OwnershipException.NotOwnedEntity();

        transferCatToAnotherOwner(cat, transferRequest.getSender(), transferRequest.getReceiver());
        transferRequestRepository.deleteById(transferRequest.getId());
    }

    @Override
    @Transactional
    public List<TransferRequestDTO> getOwnersTransferRequests(UUID catOwner) {
        CatOwner owner = ownerDAO.findById(catOwner).orElseThrow(() -> SearchException.CatOwnerNotFound());



        List<TransferRequest> requestList = transferRequestRepository.getRequestsForSpecifiedUser(owner);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();


        return requestList.stream()
                .filter(x -> TransferRequestDefaultFilters.getAbleToViewFilter().process(x, (UserDetails) authentication.getPrincipal()))
                .map(x -> TransferRequestMapper.asDTO(x)).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void sendTransferRequest(UUID catOwnerReceiver, UUID catOwnerSender, UUID catID) {
        Cat cat = catDAO.findById(catID).orElseThrow(() -> SearchException.CatNotFound());
        CatOwner sender = ownerDAO.findById(catOwnerSender).orElseThrow(() -> SearchException.CatOwnerNotFound());
        CatOwner receiver = ownerDAO.findById(catOwnerReceiver).orElseThrow(() -> SearchException.CatOwnerNotFound());

        if (catOwnerSender.equals(catOwnerReceiver))
            throw FriendshipException.SameUserRequest();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        TransferRequest transferRequest = new TransferRequest(cat, sender, receiver);

        if (!CatOwnershipDefaultFilters.getDefaultFilter().process(cat, (UserDetails) authentication.getPrincipal()) ||
            !TransferRequestDefaultFilters.getOwnershipFilter().process(transferRequest, (UserDetails) authentication.getPrincipal()))
            throw OwnershipException.NotOwnedEntity();

        transferRequestRepository.save(transferRequest);
    }

    @Transactional
    private void transferCatToAnotherOwner(Cat cat, CatOwner oldOwner, CatOwner newOwner) {

        cat.setOwner(newOwner);
        oldOwner.getCatPets().remove(cat);
        newOwner.getCatPets().add(cat);

        catDAO.save(cat);
        ownerDAO.save(newOwner);
        ownerDAO.save(oldOwner);
    }
}
