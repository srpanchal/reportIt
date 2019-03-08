package com.reportit.reportitbackend;

public interface PublisherService {

  boolean sendTweet(String tweet, String imageUrl);

  boolean sendEmailWithAttachment(String toEmail, String fromEmail, String subject,
      String body, String imageUrl);
}
