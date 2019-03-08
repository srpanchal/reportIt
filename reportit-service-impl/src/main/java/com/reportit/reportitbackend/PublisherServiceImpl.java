package com.reportit.reportitbackend;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import twitter4j.StatusUpdate;
import twitter4j.Twitter;
import twitter4j.TwitterException;

import java.io.File;
import java.io.IOException;
import java.net.URL;

@Service
@Slf4j
public class PublisherServiceImpl implements PublisherService {

  @Autowired
  private Twitter twitter;

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

}
