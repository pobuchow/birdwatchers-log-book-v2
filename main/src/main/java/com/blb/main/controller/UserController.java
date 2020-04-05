package com.blb.main.controller;

import com.blb.main.dto.UserTO;
import com.blb.main.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping(path = "/all")
    public List<UserTO> getAllUsers(){
        return userService.getAllUsers();
    }
}
