package com.reportit.reportitbackend;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequestMapping(value = (UserController.BASE_PATH))
public class UserController {
  public static final String BASE_PATH = "/user";

  @Autowired
  private UserService userService;

  @RequestMapping(method = RequestMethod.POST, value = "/save",
      produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
  public void saveUser(@RequestBody User user) {
    userService.saveUser(user);
  }

  @RequestMapping(method = RequestMethod.GET, value = "/getAll",
      produces = MediaType.APPLICATION_JSON_VALUE)
  public List<User> getAllUsers() {
    return userService.getAllUsers();
  }

    @RequestMapping(method = RequestMethod.GET, value = "/addReportedIssue",
        produces = MediaType.APPLICATION_JSON_VALUE)
    public void getAllUsers(@RequestParam String userId, @RequestParam String issueId) {
        userService.addReportedIssue(userId, issueId);
    }




}
