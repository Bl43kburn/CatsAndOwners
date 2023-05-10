package com.blackburn.dao;

import com.blackburn.model.Cat;
import com.blackburn.model.CatOwner;
import com.blackburn.model.TransferRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TransferRequestRepository extends JpaRepository<TransferRequest, UUID>, CatTransferRequestFragment {
    public List<TransferRequest> findByCat(Cat cat);

    public List<TransferRequest> findBySender (CatOwner owner);

    public List<TransferRequest> findByReceiver (CatOwner owner);

}
