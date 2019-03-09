package com.reportit.reportitbackend.impl;

import com.reportit.reportitbackend.Issue;
import com.reportit.reportitbackend.IssueRepository;
import com.reportit.reportitbackend.User;
import com.reportit.reportitbackend.UserRepository;
import com.reportit.reportitbackend.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Metrics;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private IssueRepository issueRepository;

  @Override
  public void saveUser(User user) {
    userRepository.save(user);
  }

  @Override
  public List<User> getAllUsers() {
    return userRepository.findAll();
  }

  @Override
  public void addReportedIssue(String userId, String issueId) {
        Issue issue = issueRepository.findById(issueId).get();
        addReportedIssue(userId, issue);
  }

  @Override
  public void addReportedIssue(String userId, Issue issue) {
    User user = userRepository.findById(userId).get();
    if(user.getIssuesReported() == null) {
      user.setIssuesReported(new ArrayList<>());
    }
    user.getIssuesReported().add(issue);
    userRepository.save(user);
  }

  @Override
  public String loginUser(String username, String password) {
    User user = userRepository.findByUserName(username);
    if(user != null && password.equals(user.getPassword())){
      return user.getId();
    }
    return null;
  }

  @Override
  public String signupUser(String username, String password) {
    User user = userRepository.findByUserName(username);
    if(user != null){
      return null;
    }
    user = new User();
    user.setUserName(username);
    user.setPassword(password);
    user = userRepository.save(user);
    return user.getId();
  }

  @Override
  public List<String> getFCMTokensOfNearbyUsers(GeoJsonPoint location, double distance) {
    return userRepository.findByLocationNear(location, new Distance(distance, Metrics.KILOMETERS)).stream().map(User::getFCMToken).collect(
        Collectors.toList());
  }

  @Override
  public void updateUserLocation(String userId, String location) {

  }
}
