package com.reportit.reportitbackend.impl;

import com.reportit.reportitbackend.Issue;
import com.reportit.reportitbackend.PublisherService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import twitter4j.JSONException;
import twitter4j.JSONObject;
import twitter4j.StatusUpdate;
import twitter4j.Twitter;
import twitter4j.TwitterException;

import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.util.List;

@Service
@Slf4j
public class PublisherServiceImpl implements PublisherService {

  @Autowired
  private Twitter twitter;

  @Autowired
  private Session emailSession;

    private static final String FIREBASE_SERVER_KEY = "AIzaSyD5amGeJnupzGXUF8SskKTMVTcl4bcVtE4";
    private static final String FIREBASE_API_URL = "https://fcm.googleapis.com/fcm/send";

  @Override
  public void sendTweet(String tweet, String imagePath) {
      log.info("Tweeting: " + tweet + " image: " + imagePath);
    String tweetURL = null;
    try {
      File file = new File(imagePath);
      StatusUpdate status = new StatusUpdate(tweet);
      status.setMedia(file);
      twitter.updateStatus(status);
    } catch (TwitterException e) {
      log.error("Failed to send tweet {} , imagePath : {}", tweet, imagePath, e);
    }
  }

    @Override
    public String createTweet(Issue issue, List<String> tweeterHandles) {
        StringBuilder str = new StringBuilder();
        str.append("Issue Reported: ").append(issue.getTitle()).append("\n")
                .append(issue.getDescription()).append("\n")
                .append("Location: ").append(issue.getAddress()).append("\n")
                .append("Votes: ").append(issue.getVotes()).append("\n")
                .append("Category: ").append(issue.getCategory()).append("\n")
                .append("Please help in resolving this issue.").append("\n");
        tweeterHandles.forEach(t -> str.append("@").append(t).append(" "));
        return str.toString();
    }

    @Override
  public boolean sendEmailWithAttachment(String toEmail, String fromEmail, String subject,
      String content, String imagePath) {
    boolean sent = false;
    try {
      Message message = new MimeMessage(emailSession);
      message.setFrom(new InternetAddress(fromEmail));
      message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
      message.setSubject(subject);
      File file = new File(imagePath);
      MimeBodyPart mimeBodyPart = new MimeBodyPart();
      mimeBodyPart.setContent(content, "text/html");
      mimeBodyPart.attachFile(file);
      Multipart multipart = new MimeMultipart();
      multipart.addBodyPart(mimeBodyPart);
      message.setContent(multipart);
      Transport.send(message);
      sent = true;
    } catch (Exception e) {
      log.error("Failed to send email to {}", toEmail, e);
    }
    return sent;
  }

    @Override
  public String sendPushNotifications(String title, String message, List<String> tokens) {
    RestTemplate restTemplate = new RestTemplate();
    HttpHeaders httpHeaders = new HttpHeaders();
    httpHeaders.set("Authorization", "key=" + FIREBASE_SERVER_KEY);
    httpHeaders.set("Content-Type", "application/json");
    JSONObject msg = new JSONObject();
    JSONObject json = new JSONObject();
    try {
      msg.put("title", title);
      msg.put("body", message);
      msg.put("notificationType", "Test");

      json.put("data", msg);
      json.put("registration_ids", tokens);
    } catch (JSONException e) {
      log.error("Failed to send push notification", e);
    }

    HttpEntity<String> httpEntity = new HttpEntity<>(json.toString(), httpHeaders);
    String response = restTemplate.postForObject(FIREBASE_API_URL, httpEntity, String.class);
    return response;
  }

}
