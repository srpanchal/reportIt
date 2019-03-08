package com.reportit.reportitbackend;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

@Configuration
public class TwitterConfig {

  @Bean
  private Twitter twitter(){
    ConfigurationBuilder cb = new ConfigurationBuilder();
    cb.setDebugEnabled(true)
        .setOAuthConsumerKey("29Blwo4vufsCuxXbzYX7KWQRz")
        .setOAuthConsumerSecret("jU2Y64kbOijCq3pHipIshtDcFjeYnAp4lHAWDemJg75hJAKK5C")
        .setOAuthAccessToken("1104008992802721792-vAkgvVgYY7suPpW3ODVsY704fA6Vtx")
        .setOAuthAccessTokenSecret("lmA5GO8Lqg7R6n2ZJsRfwlAQdQPKjLOibKWJq3NmQpS7H");
    TwitterFactory tf = new TwitterFactory(cb.build());
    Twitter twitter = tf.getInstance();
    return twitter;
  }
}
