package com.blb.main.controller;

import com.blb.main.dto.UserTO;
import com.blb.main.service.exception.UserCreationException;
import com.blb.main.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping(path = "/add")
    @ResponseBody
    public UserTO addNewUser(@RequestParam(value = "username") String username, @RequestParam("email") String email) throws UserCreationException {
        return userService.insertUser(username, email);
    }
}
