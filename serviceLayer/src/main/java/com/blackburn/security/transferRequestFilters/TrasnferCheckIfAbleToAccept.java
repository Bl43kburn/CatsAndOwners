package com.blackburn.security.transferRequestFilters;

import com.blackburn.model.TransferRequest;
import com.blackburn.security.FilterChain;
import com.blackburn.security.FilterChainAbstractClass;
import org.springframework.security.core.userdetails.UserDetails;

public class TrasnferCheckIfAbleToAccept extends FilterChainAbstractClass<TransferRequest> {
    @Override
    public FilterChain<TransferRequest> setNext(FilterChain<TransferRequest> filterChain) {
        return super.setNext(filterChain);
    }

    @Override
    public boolean process(TransferRequest entity, UserDetails authority) {
        if (authority.getUsername().equals(entity.getReceiver().getUsername()))
            return true;
        return super.process(entity, authority);
    }
}