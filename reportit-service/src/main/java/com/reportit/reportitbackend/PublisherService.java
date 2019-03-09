package com.reportit.reportitbackend;

import java.util.List;

public interface PublisherService {

  String sendTweet(String tweet, String imageUrl);

  boolean sendEmailWithAttachment(String toEmail, String fromEmail, String subject,
      String body, String imagePath);

  String sendPushNotifications(String title, String message, List<String> tokens);

    String createTweet(Issue issue);
}
