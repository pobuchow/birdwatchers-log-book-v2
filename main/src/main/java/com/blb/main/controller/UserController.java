package com.blb.main.controller;

import com.blb.main.dto.UserTO;
import com.blb.main.service.UserService;
import com.blb.main.service.exception.UserCreationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping(path = "/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping(path = "/add")
    @ResponseBody
    public UserTO addNewUser(@RequestParam(value = "username") String username,
                             @RequestParam(value = "password") String password,
                             @RequestParam(value = "email") String email) throws UserCreationException {
        try {
            return userService.insertUser(username, password, email);
        }catch(UserCreationException ex){
            throw new ResponseStatusException(
                    HttpStatus.UNPROCESSABLE_ENTITY, ex.getMessage(), ex);
        }
    }
}
