package com.reportit.reportitbackend;

import java.util.List;

public interface PublisherService {

  void sendTweet(String tweet, String imageUrl);

  boolean sendEmailWithAttachment(String toEmail, String fromEmail, String subject,
      String body, String imagePath);

    String createTweet(Issue issue, List<String> tweeterHandles);

    String sendPushNotifications(String title, String message, List<String> tokens);
}
