package com.blackburn.security.catOwnershipFilter;

import com.blackburn.model.Cat;
import com.blackburn.security.FilterChain;
import com.blackburn.security.FilterChainAbstractClass;
import org.springframework.security.core.userdetails.UserDetails;

public class CatOwnershipAuthorisationFilter extends FilterChainAbstractClass<Cat> {
    @Override
    public FilterChain<Cat> setNext(FilterChain<Cat> filterChain) {
        return super.setNext(filterChain);
    }

    @Override
    public boolean process(Cat entity, UserDetails authority) {
        return next.process(entity, authority);
    }
}
