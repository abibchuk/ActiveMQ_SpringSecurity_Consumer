package com.anjey.consumer.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;


@Configuration
@EnableWebSecurity

public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private TokenAuthenticationProvider authProvider;

    @Autowired
    private TokenAuthenticationFilter tokenAuthenticationFilter;

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authProvider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .addFilterBefore(tokenAuthenticationFilter, BasicAuthenticationFilter.class)
            .authorizeRequests()
                .antMatchers("/get_message").access("hasRole('ROLE_ADMIN')")
                .antMatchers("/show_message").access("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
                .antMatchers("/my_login").access("permitAll()")
//                .anyRequest().authenticated()
//                .and().formLogin().loginPage("/my_login").permitAll()
//                .defaultSuccessUrl("/get_message")
//                .failureUrl("/failure").usernameParameter("username").passwordParameter("password")
//                .loginProcessingUrl("/j_spring_security_check")
                .and()
                .logout().logoutSuccessUrl("/my_login?logout")
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().csrf().disable();
    }

    @Bean
    public AuthenticationEntryPoint tokenAuthenticationEntryPoint() {
        return new TokenAuthenticationEntryPoint();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder;
    }

}
