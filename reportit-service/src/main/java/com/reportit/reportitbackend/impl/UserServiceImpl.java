package com.reportit.reportitbackend.impl;

import com.reportit.reportitbackend.UserRepository;
import com.reportit.reportitbackend.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

}
