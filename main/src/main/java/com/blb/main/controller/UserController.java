package com.blb.main.controller;

import com.blb.main.dto.LoginCredentialsTO;
import com.blb.main.dto.UserTO;
import com.blb.main.entity.exception.EmailValidationFailedException;
import com.blb.main.entity.exception.LoginValidationFailedException;
import com.blb.main.service.UserService;
import com.blb.main.service.exception.UserAuthenticationException;
import com.blb.main.service.exception.UserCreationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping(path = "/users")
public class UserController {

    @Autowired
    private UserService userService;

    @ResponseBody
    @PostMapping(path = "/add")
    public UserTO addNewUser(@RequestParam(value = "username") String username,
                             @RequestParam(value = "password") String password,
                             @RequestParam(value = "email") String email) throws UserCreationException {
        try {
            return userService.insertUser(username, password, email);
        }catch(UserCreationException | EmailValidationFailedException | LoginValidationFailedException ex){
            throw new ResponseStatusException(
                    HttpStatus.UNPROCESSABLE_ENTITY, ex.getMessage(), ex);
        }
    }

    @ResponseBody
    @PostMapping(path = "/authenticate")
    public LoginCredentialsTO authenticate(@RequestBody LoginCredentialsTO user) throws UserAuthenticationException {
        return userService.authorize(user.getUsername(), user.getPassword());
    }
}
