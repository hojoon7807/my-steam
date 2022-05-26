package com.hojoon.mysteam;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class MysteamApplication {

  public static void main(String[] args) {
    SpringApplication.run(MysteamApplication.class, args);
  }

}
