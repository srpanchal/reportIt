package com.reportit.reportitbackend.impl;

import com.reportit.reportitbackend.Issue;
import com.reportit.reportitbackend.IssueRepository;
import com.reportit.reportitbackend.User;
import com.reportit.reportitbackend.UserRepository;
import com.reportit.reportitbackend.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
  public void addReportedIssue(String userId, String isuueId) {
        Issue issue = issueRepository.findById(isuueId).get();
        User user = userRepository.findById(userId).get();
        user.getIssuesReported().add(issue);
  }
}
