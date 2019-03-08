package com.reportit.reportitbackend;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
import java.io.IOException;
import java.net.URL;

@Service
@Slf4j
public class PublisherServiceImpl implements PublisherService {

  @Autowired
  private Twitter twitter;

  @Autowired
  private Session emailSession;

  public boolean sendTweet(String tweet, String imageUrl) {
    boolean isSent = false;
    try {
      File file = new File("temp.jpeg");
      FileUtils.copyURLToFile(new URL(imageUrl), file, 10000, 1000);
      StatusUpdate status = new StatusUpdate(tweet);
      status.setMedia(file);
      twitter.updateStatus(status);
      isSent = true;
    } catch (IOException | TwitterException e) {
      log.error("Failed to send tweet {} , imageUrl : {}", tweet, imageUrl, e);
    }
    return isSent;
  }

  public boolean sendEmailWithAttachment(String toEmail, String fromEmail, String subject,
      String content, String imageUrl) {
    boolean sent = false;
    try {
      Message message = new MimeMessage(emailSession);
      message.setFrom(new InternetAddress("from@gmail.com"));
      message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("to@gmail.com"));
      message.setSubject("Mail Subject");
      File file = new File("temp_mail.jpeg");
      FileUtils.copyURLToFile(new URL(imageUrl), file, 10000, 1000);
      MimeBodyPart mimeBodyPart = new MimeBodyPart();
      mimeBodyPart.setContent(content, "text/html");
      mimeBodyPart.attachFile(new File("path/to/file"));
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
}
