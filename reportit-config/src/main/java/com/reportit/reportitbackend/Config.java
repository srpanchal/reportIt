package com.reportit.reportitbackend;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import java.util.Properties;

@Configuration
public class Config {

  @Bean
  public Twitter twitter(){
    ConfigurationBuilder cb = new ConfigurationBuilder();
    cb.setDebugEnabled(true).setOAuthConsumerKey("29Blwo4vufsCuxXbzYX7KWQRz")
            .setOAuthConsumerSecret("jU2Y64kbOijCq3pHipIshtDcFjeYnAp4lHAWDemJg75hJAKK5C")
            .setOAuthAccessToken("1104008992802721792-vAkgvVgYY7suPpW3ODVsY704fA6Vtx")
            .setOAuthAccessTokenSecret("lmA5GO8Lqg7R6n2ZJsRfwlAQdQPKjLOibKWJq3NmQpS7H");
    TwitterFactory tf = new TwitterFactory(cb.build());
    Twitter twitter = tf.getInstance();
    return twitter;
  }

  @Bean
  public Session emailSession(){
    Properties prop = new Properties();
    prop.put("mail.smtp.auth", true);
    prop.put("mail.smtp.starttls.enable", "true");
    prop.put("mail.smtp.host", "smtp.gmail.com");
    prop.put("mail.smtp.port", "25");
    prop.put("mail.smtp.ssl.trust", "smtp.gmail.com");
    Session session = Session.getInstance(prop, new Authenticator() {
      @Override
      protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication("report.it.pls@gmail.com", "Test@123");
      }
    });
    return session;
  }
}
