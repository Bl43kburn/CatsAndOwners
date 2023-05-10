package com.blackburn.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class TransferRequestDTO {

    private UUID id;

    private UUID sender;

    private UUID receiver;

    private UUID cat;
}
