package com.blackburn.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@AllArgsConstructor
public class CatTransferRequestModel {

    @Getter
    private UUID catId;

    @Getter
    private UUID newOwnerId;

    @Getter
    private UUID oldOwnerId;
}
