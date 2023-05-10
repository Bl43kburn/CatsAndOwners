package com.blackburn.security.catOwnershipFilter;

import com.blackburn.model.Cat;
import com.blackburn.security.FilterChain;
import com.blackburn.security.FilterChainAbstractClass;
import org.springframework.security.core.userdetails.UserDetails;

public class CatOwnershipCheckIfRoot extends FilterChainAbstractClass<Cat> {

    @Override
    public FilterChain<Cat> setNext(FilterChain<Cat> filterChain) {
        return super.setNext(filterChain);
    }

    @Override
    public boolean process(Cat entity, UserDetails authority) {
        if (authority.getAuthorities().stream().anyMatch(x -> x.getAuthority().equals("root")))
            return true;
        return super.process(entity, authority);
    }
}