package com.blb.controller;

import com.blb.dto.UserTO;
import com.blb.service.UserService;
import com.blb.service.exception.UserCreationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping(path = "/users/")
public class UserController {

    @Autowired
    private UserService userService;

    @ResponseBody
    @PostMapping(path = "create")
    public UserTO create(@RequestParam(value = "username") String username,
                         @RequestParam(value = "password") String password,
                         @RequestParam(value = "email") String email) {
        try {
            return userService.insertUser(username, password, email);
        } catch (UserCreationException e) {
            throw new ResponseStatusException(
                    HttpStatus.UNPROCESSABLE_ENTITY, "User cannot be created", e);
        }
    }
}
