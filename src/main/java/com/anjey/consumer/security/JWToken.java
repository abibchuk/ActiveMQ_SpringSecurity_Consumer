package com.anjey.consumer.security;


import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class JWToken extends AbstractAuthenticationToken {

    private final String token;

    public JWToken(String token) {
        super(null);
        this.token = token;
    }

    public JWToken(String token, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.token = token;
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public String getPrincipal() {
        return token;
    }
}