package com.blackburn.model;


import com.blackburn.security.Role;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "authority")
public class Authority {

    @Id
    @Getter
    @Setter
    private UUID id;

    @Getter
    @Setter
    private String login;

    @Getter
    @Setter
    private String password;

    @Getter
    @Setter
    @Enumerated(value = EnumType.STRING)
    private Role role;

    public Authority(String login, String password, Role role) {
        this.login = login;
        this.password = password;
        this.role = role;
        id = UUID.randomUUID();
    }

    protected Authority() {
    }
}
