package com.blackburn.dao;

import com.blackburn.model.CatOwner;
import com.blackburn.model.TransferRequest;

import java.util.List;
import java.util.UUID;

public interface CatTransferRequestFragment {
    public List<TransferRequest> getRequestsForSpecifiedUser(CatOwner owner);
}
