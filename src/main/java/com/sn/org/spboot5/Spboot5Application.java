package com.sn.org.spboot5;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class Spboot5Application {

  public static void main(String[] args) {
    SpringApplication.run(Spboot5Application.class, args);
  }

}
