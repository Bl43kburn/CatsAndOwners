package com.blackburn.mapping;

import com.blackburn.DTO.TransferRequestDTO;
import com.blackburn.model.TransferRequest;

public class TransferRequestMapper {

    public static TransferRequestDTO asDTO(TransferRequest request) {
        return new TransferRequestDTO(request.getId(), request.getSender().getId(), request.getReceiver().getId(), request.getCat().getId());
    }
}
