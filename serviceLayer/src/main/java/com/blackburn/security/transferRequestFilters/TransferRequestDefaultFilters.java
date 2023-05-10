package com.blackburn.security.transferRequestFilters;

import com.blackburn.model.TransferRequest;
import com.blackburn.security.FilterChain;

public class TransferRequestDefaultFilters {
    public static FilterChain<TransferRequest> getOwnershipFilter() {
        return new TransferCheckIfOwner().setNext(new TransferCheckIfRoot());
    }

    public static FilterChain<TransferRequest> getAbleToViewFilter() {
        return new TransferCheckIfAbleToView().setNext(new TransferCheckIfRoot());
    }

    public static FilterChain<TransferRequest> getAbleToAcceptFilter(){
        return new TrasnferCheckIfAbleToAccept().setNext(new TransferCheckIfRoot());
    }
}
