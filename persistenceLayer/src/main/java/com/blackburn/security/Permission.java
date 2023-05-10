package com.blackburn.security;

public enum Permission {
    RootAuthority("root"),
    OwnerAuthority("owner");
    private final String permission;

    Permission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }

}
