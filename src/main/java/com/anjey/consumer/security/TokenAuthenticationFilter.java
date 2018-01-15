package com.anjey.consumer.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class TokenAuthenticationFilter extends GenericFilterBean {

private static final Logger log = LoggerFactory.getLogger(TokenAuthenticationFilter.class);

    @Autowired
    private TokenAuthenticationProvider authProvider;

    @Value("${security.header.name}")
    private String header;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;

        String headerValue = httpServletRequest.getHeader(header);
        if (headerValue==null) {
            log.debug("Token is null");
//          httpServletResponse.sendRedirect("/my_login");
//          (httpServletResponse).sendError(HttpServletResponse.SC_UNAUTHORIZED, "No JWT token found in request headers");
        }
        else {
            log.debug("Token from filter:"+ headerValue);
            Authentication authentication = authProvider.authenticate(new JWToken(headerValue));
            SecurityContextHolder.getContext().setAuthentication(authentication);

        }

        filterChain.doFilter(servletRequest, servletResponse);

    }
}