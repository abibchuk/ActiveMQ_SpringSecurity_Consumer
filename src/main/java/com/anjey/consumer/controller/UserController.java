package com.anjey.consumer.controller;

import com.anjey.consumer.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public @ResponseBody
    void createUser(@RequestParam("username") String username,
                      @RequestParam("password") String password,
                      @RequestParam("roles") String roles) {
        log.info("POST Params: username="+ username + ", password="+ password +", roles=" + roles);
        userService.createUser(username, passwordEncoder.encode(password), Arrays.stream(roles.substring(1, roles.length()-1).split(",")).collect(Collectors.toSet()));

    }

    @RequestMapping(value = "/my_login", method = RequestMethod.GET)
    public String loginPage() {
        return "my_login";
    }

    @RequestMapping(value = "/failure", method = RequestMethod.GET)
    public String failurePage() {
        return "failure";
    }

}
