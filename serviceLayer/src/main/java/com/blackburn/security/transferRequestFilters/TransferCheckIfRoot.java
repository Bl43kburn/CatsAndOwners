package com.blackburn.security.transferRequestFilters;

import com.blackburn.model.TransferRequest;
import com.blackburn.security.FilterChain;
import com.blackburn.security.FilterChainAbstractClass;
import com.blackburn.security.Permission;
import org.springframework.security.core.userdetails.UserDetails;

public class TransferCheckIfRoot extends FilterChainAbstractClass<TransferRequest> {
    @Override
    public FilterChain<TransferRequest> setNext(FilterChain<TransferRequest> filterChain) {
        return super.setNext(filterChain);
    }

    @Override
    public boolean process(TransferRequest entity, UserDetails authority) {
        if (authority.getAuthorities().stream().allMatch(x -> x.getAuthority().equals(Permission.RootAuthority.getPermission())))
            return true;
        return super.process(entity, authority);
    }
}
