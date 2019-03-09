package com.reportit.reportitbackend;

import java.util.List;

public interface UserService {
    void saveUser(User user);
    List<User> getAllUsers();
    void addReportedIssue(String userId, String isuueId);
    void addReportedIssue(String userId, Issue isuue);
    String loginUser(String username, String password);
    String signupUser(String username, String password);
}
