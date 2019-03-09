package com.reportit.reportitbackend;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequestMapping(value = (UserController.BASE_PATH))
@Api(value = "User controller")
public class UserController {
  public static final String BASE_PATH = "/user";

  @Autowired
  private UserService userService;

  @RequestMapping(method = RequestMethod.POST, value = "/save",
      produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
  @ApiOperation(value = "save")
  public void saveUser(@RequestBody User user) {
    userService.saveUser(user);
  }

  @RequestMapping(method = RequestMethod.GET, value = "/getAll",
      produces = MediaType.APPLICATION_JSON_VALUE)
  @ApiOperation(value = "getAll")
  public List<User> getAllUsers() {
    return userService.getAllUsers();
  }

    @RequestMapping(method = RequestMethod.GET, value = "/addReportedIssue",
        produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "addReportedIssue")
    public void getAllUsers(@RequestParam String userId, @RequestParam String issueId) {
        userService.addReportedIssue(userId, issueId);
    }

    @GetMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password){
      return userService.loginUser(username, password);
    }

  @GetMapping("/signup")
  public String signup(@RequestParam String username, @RequestParam String password){
    return userService.signupUser(username, password);
  }

}
