package com.sn.org.spboot5.utils;

import com.sn.org.spboot5.services.CursFromApi;
import java.util.Random;
import lombok.Getter;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@Getter
public class RunAfterStartUp implements CursFromApi {

  private double curs = 21000;
  @EventListener(ApplicationReadyEvent.class)
  public void runAfterStartup() {
    Random random = new Random();

    new Thread(() -> {
      double changed;
      while (true){
        changed = curs * random.nextInt(5) / 100;
        curs += random.nextBoolean() ? - changed : changed;
        try {
          Thread.currentThread().sleep(500);
        } catch (InterruptedException e) {
          throw new RuntimeException(e);
        }
      }
    }).start();

  }
}