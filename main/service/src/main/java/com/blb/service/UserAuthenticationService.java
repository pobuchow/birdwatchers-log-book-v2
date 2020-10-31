package com.blb.service;

import com.blb.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserAuthenticationService implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username){
        User user = userService.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
        return new UserAuthentication(user.getLogin());
    }
}
