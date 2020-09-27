package com.blb.main.controller;

import com.blb.main.dto.UserTO;
import com.blb.main.service.UserService;
import com.blb.main.service.exception.UserCreationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/users")
public class UserController {

    @Autowired
    private UserService userService;

    @ResponseBody
    @PostMapping(path = "/create")
    public UserTO create(@RequestParam(value = "username") String username,
                         @RequestParam(value = "password") String password,
                         @RequestParam(value = "email") String email) throws UserCreationException {
        return userService.insertUser(username, password, email);
    }
}
