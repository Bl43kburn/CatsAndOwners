package com.blackburn.controllers;

import com.blackburn.DTO.TransferRequestDTO;
import com.blackburn.mapping.TransferRequestMapper;
import com.blackburn.models.CatTransferRequestModel;
import com.blackburn.services.CatTransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RequestMapping("catTransfer")
@RestController
public class CatTransferController {
    private final CatTransferService service;

    @Autowired
    public CatTransferController(@Qualifier("standardCatTransferService") CatTransferService service) {
        this.service = service;
    }

    @PostMapping("/send")
    public void transferCat(@RequestBody CatTransferRequestModel model) {
        service.sendTransferRequest(model.getNewOwnerId(), model.getOldOwnerId(), model.getCatId());
    }

    @PostMapping("/accept/{transferRequestID}")
    public void acceptRequest(@PathVariable UUID transferRequestID) {
        service.acceptRequest(transferRequestID);
    }

    @GetMapping("/viewRequests/{catOwnerID}")
    public List<TransferRequestDTO> getRequests(@PathVariable UUID catOwnerID) {
        return service.getOwnersTransferRequests(catOwnerID);
    }
}
