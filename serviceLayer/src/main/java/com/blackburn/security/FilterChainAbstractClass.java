package com.blackburn.security;

import org.springframework.security.core.userdetails.UserDetails;

public abstract class FilterChainAbstractClass<T> implements FilterChain<T> {

    protected FilterChain<T> next;

    @Override
    public FilterChain<T> setNext(FilterChain<T> filterChain) {
        next = filterChain;
        return this;
    }

    @Override
    public boolean process(T entity, UserDetails authority) {
        if (next == null)
            return false;
        else
            return next.process(entity, authority);
    }
}
