package com.zoey.ppmtools.services;

import com.zoey.ppmtools.domain.User;
import com.zoey.ppmtools.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public User saveUser(User newUser) {
//        try {
//            newUser.setPassword(bCryptPasswordEncoder.encode(newUser.getPassword()));
//            // username unique (exception)
//            // make sure confirm password and password match
//            // we dont persist or show the confirm password
//            return userRepository.save(newUser);
//        } catch (Exception e) {
//            // handle exceptions
//        }
        newUser.setPassword(bCryptPasswordEncoder.encode(newUser.getPassword()));
        return userRepository.save(newUser);
    }


}
