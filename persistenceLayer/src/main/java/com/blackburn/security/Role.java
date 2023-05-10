package com.blackburn.security;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

public enum Role {
    ROOT(Set.of(Permission.RootAuthority)),
    OWNER(Set.of(Permission.OwnerAuthority));
    private final Set<Permission> permissions;

    Role(Set<Permission> permissions) {
        this.permissions = permissions;
    }

    public Set<Permission> getPermissions() {
        return Collections.unmodifiableSet(permissions);
    }

    public Set<SimpleGrantedAuthority> getAuthorities() {
        return getPermissions().stream()
                .map(x -> new SimpleGrantedAuthority(x.getPermission()))
                .collect(Collectors.toSet());
    }
}
