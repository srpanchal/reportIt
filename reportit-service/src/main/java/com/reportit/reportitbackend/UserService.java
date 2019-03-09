package com.reportit.reportitbackend;

import org.springframework.data.mongodb.core.geo.GeoJsonPoint;

import java.util.List;

public interface UserService {
    void saveUser(User user);
    List<User> getAllUsers();
    void addReportedIssue(String userId, String isuueId);
    void addReportedIssue(String userId, Issue isuue);
    String loginUser(String username, String password);
    String signupUser(String username, String password, String email, String phoneNo, String location,String gcmToken, double latitude, double longitude);
    List<String> getFCMTokensOfNearbyUsers(GeoJsonPoint location, double distance);
    void updateUserLocation(String userId, String location);
}
