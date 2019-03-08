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
  private Twitter twitter(){
    ConfigurationBuilder cb = new ConfigurationBuilder();
    cb.setDebugEnabled(true)
        .setOAuthConsumerKey("-")
        .setOAuthConsumerSecret("-")
        .setOAuthAccessToken("-")
        .setOAuthAccessTokenSecret("-");
    TwitterFactory tf = new TwitterFactory(cb.build());
    Twitter twitter = tf.getInstance();
    return twitter;
  }

  @Bean
  private Session emailSession(){
    Properties prop = new Properties();
    prop.put("mail.smtp.auth", true);
    prop.put("mail.smtp.starttls.enable", "true");
    prop.put("mail.smtp.host", "smtp.mailtrap.io");
    prop.put("mail.smtp.port", "25");
    prop.put("mail.smtp.ssl.trust", "smtp.mailtrap.io");
    Session session = Session.getInstance(prop, new Authenticator() {
      @Override
      protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication("report.it.pls@gmail.com", "");
      }
    });
    return session;
  }
}
