package com.chunjae.project05.domain;

import org.springframework.security.core.GrantedAuthority;

public class UserGrant implements GrantedAuthority {

    @Override
    public String getAuthority() {
        return "ADMIN";
    }

}