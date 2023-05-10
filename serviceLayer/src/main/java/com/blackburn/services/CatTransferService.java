package com.blackburn.services;

import com.blackburn.DTO.TransferRequestDTO;
import com.blackburn.model.TransferRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface CatTransferService {
    public void acceptRequest(UUID requestID);

    public List<TransferRequestDTO> getOwnersTransferRequests(UUID catOwner);

    public void sendTransferRequest(UUID catOwnerReceiver, UUID catOwnerSender, UUID catID);
}
