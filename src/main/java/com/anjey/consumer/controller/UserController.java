package com.anjey.consumer.controller;

import com.anjey.consumer.service.TokenService;
import com.anjey.consumer.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;
import java.util.stream.Collectors;

@Controller
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @Value("${security.header.name}")
    private String securityHeader;

    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private TokenService tokenService;

    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public @ResponseBody
    void createUser(@RequestParam("username") String username,
                      @RequestParam("password") String password,
                      @RequestParam("roles") String roles) {
        log.info("POST Params: username="+ username + ", password="+ password +", roles=" + roles);
        userService.createUser(username, password, Arrays.stream(roles.substring(1, roles.length()-1).split(",")).collect(Collectors.toSet()));

    }

    @RequestMapping(value = "/my_login", method = RequestMethod.GET)
    public String loginPage() {
        return "my_login";
    }

    @RequestMapping(value = "/my_login", method = RequestMethod.POST)
    public ResponseEntity tokenResponse(@RequestParam("username") String username,
                                     @RequestParam("password") String password) {
        try {
            String tokenByCreds = tokenService.getTokenByCreds(username, password);
            log.debug("Generated token: "+ tokenByCreds);
            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.set(securityHeader,tokenByCreds);
            return new ResponseEntity<>(tokenByCreds, responseHeaders, HttpStatus.OK);

        } catch(AuthenticationCredentialsNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credentials Not Found");
        }
    }

    @RequestMapping(value = "/failure", method = RequestMethod.GET)
    public String failurePage() {
        return "failure";
    }

}
