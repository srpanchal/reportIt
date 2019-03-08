package com.reportit.reportitbackend;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

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
  
}
