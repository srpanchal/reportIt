package com.reportit.reportitbackend;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping(value = (UserController.BASE_PATH))
public class UserController {
    public static final String BASE_PATH = "/user";

    @Autowired
    private UserService userService;


}
