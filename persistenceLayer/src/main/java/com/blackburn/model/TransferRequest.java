package com.blackburn.model;


import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.UUID;

@Entity
public class TransferRequest {
    @Id
    @Getter
    @Setter
    private UUID id;

    @Getter
    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private CatOwner sender;

    @Getter
    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private CatOwner receiver;

    @Getter
    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Cat cat;

    public TransferRequest(Cat cat, CatOwner sender, CatOwner receiver) {
        id = UUID.randomUUID();
        this.sender = sender;
        this.receiver = receiver;
        this.cat = cat;
    }

    protected TransferRequest() {
    }
}
