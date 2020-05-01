package com.blb.main.service;

import com.blb.main.dao.UserRepository;
import com.blb.main.dto.UserTO;
import com.blb.main.entity.User;
import com.blb.main.service.exception.UserCreationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolationException;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public UserTO insertUser(String userName, String password, String email) throws UserCreationException {
        final User userToSave = new User(userName, password, email);
        if(userRepository.findByLoginUserName(userName).isPresent()) throw new UserCreationException("UserName must be unique");
        User savedUser = null;
        try {
            savedUser = userRepository.save(userToSave);
        }catch (ConstraintViolationException ex){
            throw new UserCreationException(ex.getMessage());
        }
        return new UserTO(savedUser);
    }
}
