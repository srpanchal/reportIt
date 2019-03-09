package com.reportit.reportitbackend;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
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

    @PostMapping("/login")
    public LoginDto login(@RequestBody  LoginDto loginDto){
      String userId =  userService.loginUser(loginDto.getUsername(), loginDto.getPassword());
      loginDto.setUserId(userId);
      return loginDto;
    }

  @PostMapping("/signup")
  public LoginDto signup(@RequestBody LoginDto loginDto){
     String userId = userService.signupUser(loginDto.getUsername(), loginDto.getPassword(), loginDto.getEmail(), loginDto.getPhoneNo(), loginDto.getLocation(), loginDto.getGcmToken());
     loginDto.setUserId(userId);
     return loginDto;
  }

  @PostMapping("/updateUserLocation")
  public LoginDto updateUserLocation(@RequestBody LoginDto loginDto){
    userService.updateUserLocation(loginDto.getUserId(), loginDto.getLocation());
    return loginDto;
  }

}
