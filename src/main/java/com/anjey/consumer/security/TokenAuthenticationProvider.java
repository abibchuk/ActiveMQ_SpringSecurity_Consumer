package com.anjey.consumer.security;

import com.anjey.consumer.service.TokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

@Component
public class TokenAuthenticationProvider implements AuthenticationProvider {

    private static final Logger log = LoggerFactory.getLogger(TokenAuthenticationProvider.class);

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UserDetailsService userDetailsService;

    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String token = (String) authentication.getPrincipal();
        log.debug("Token: "+ token);

        User userFromToken = tokenService.getUserByToken(token);

        if (userFromToken == null) {
            throw new BadCredentialsException("Username not found.");
        }

        UserDetails userFromDB = userDetailsService.loadUserByUsername(userFromToken.getUsername());
        if (userFromDB == null) {
            throw new BadCredentialsException("User not found by username from token");
        }

        if (!userFromToken.getPassword().equals(userFromDB.getPassword())) {
            throw new BadCredentialsException ("Bad credentials in token");
        }

        return new UsernamePasswordAuthenticationToken(
                userFromDB, userFromDB, userFromDB.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals( UsernamePasswordAuthenticationToken.class );
    }

}