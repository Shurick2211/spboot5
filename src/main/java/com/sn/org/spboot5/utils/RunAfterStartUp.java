package com.sn.org.spboot5.utils;

import com.sn.org.spboot5.models.Person;
import com.sn.org.spboot5.models.PlayAccount;
import com.sn.org.spboot5.services.CheckCursService;
import com.sn.org.spboot5.services.CursFromApi;
import java.util.Random;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@Getter
public class RunAfterStartUp implements CursFromApi {
  @Value("${start.summ.fiat}")
  private double summ;
  @Value("${range.prize.curs}")
  private double rangeCursForBuy;

  @Autowired
  private CheckCursService checkCursService;

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

    checkCursService.subscribeToCheck(new Person(summ,
        new PlayAccount(summ, 0, AccountState.FIAT, rangeCursForBuy), false));

  }
}