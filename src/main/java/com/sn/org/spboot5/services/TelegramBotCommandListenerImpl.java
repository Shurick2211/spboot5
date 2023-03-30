package com.sn.org.spboot5.services;

import com.sn.org.spboot5.models.Person;
import com.sn.org.spboot5.models.PlayAccount;
import com.sn.org.spboot5.utils.AccountState;
import java.util.NoSuchElementException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TelegramBotCommandListenerImpl implements TelegramBotCommandListener {
  @Value("${start.summ.fiat}")
  private double summ;
  @Value("${range.prize.curs.percent}")
  private double rangeCursForBuy;

  @Value("${api.key}")
  private String apiKey;
  @Value("${api.secret}")
  private String apiSecret;

  @Autowired
  private BuySellService buySellService;
  @Autowired
  private BuySellServiceApi buySellServiceApi;
  @Autowired
  private CursFromApi cursFromApi;


  @Override
  public String buy(String id) {
    Person person;
    try {
      person = CheckCursServiceImpl.getPersonByTelegramId(id);
      return "Person - " + person.getTelegramId() + " in the game!";
    }catch (NoSuchElementException e){
      person = new Person(id,summ,
          new PlayAccount(summ, 0, AccountState.FIAT, rangeCursForBuy),
          false,  apiKey,  apiSecret, 5);
      CheckCursServiceImpl.subscribeToCheck(person);
      return "Start!";
    }
  }

  @Override
  public String sell(String id) {
    Person person = CheckCursServiceImpl.getPersonByTelegramId(id);

    log.info("Stop Game Sell FIAT Summ = {} ", summ);
    if (CheckCursServiceImpl.stopGame(person)) {
      return "Stop Game Sell FIAT Summ = " + person.getPlayAccount().getSumm();
    }
    return "Tyt sell";
  }

  @Override
  public String curs(String id) {
    return cursFromApi.getCurs()+"";
  }

  @Override
  public String walletInfo(String id) {
    try {
      Person person = CheckCursServiceImpl.getPersonByTelegramId(id);
      return walletInfo(person);
    }catch (NoSuchElementException e) {
      return "You aren't of game";
    }
  }

  @Override
  public String registration(Person person) {
    return "Registration!";
  }

  public String walletInfo(Person person) {
    return buySellServiceApi.getWallet(person);
  }


}
