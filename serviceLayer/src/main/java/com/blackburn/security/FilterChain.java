package com.blackburn.security;

import org.springframework.security.core.userdetails.UserDetails;

public interface FilterChain<T> {

     FilterChain<T> setNext(FilterChain<T> filterChain);

     boolean process(T entity, UserDetails authority);
}
