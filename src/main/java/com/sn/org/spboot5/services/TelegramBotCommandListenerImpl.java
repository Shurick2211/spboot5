package com.sn.org.spboot5.services;

import com.sn.org.spboot5.models.Person;
import com.sn.org.spboot5.models.PlayAccount;
import com.sn.org.spboot5.utils.AccountState;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TelegramBotCommandListenerImpl implements TelegramBotCommandListener {

  //Temporary test data start<
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
  // >end Temporary test data


  @Autowired
  private BuySellServiceApi buySellServiceApi;

  private final Set<Person> registrationPersons  =  new HashSet<>();


  @Override
  public String buy(String id) {
    try {
      Person person = registrationPersons.stream()
          .filter(p -> p.getTelegramId().equals(id)).findFirst().orElseThrow();
      CheckCursServiceImpl.subscribeToCheck(person);
      if (person.getPlayAccount().getAccState() == AccountState.FIAT) {
        buySellService.buyCoin(person);
        return  "New player - " + person.getTelegramId() +
            "\nBuying coins BTC = " + person.getPlayAccount().getSumm()
            + "  curs = " +  person.getPlayAccount().getStartPeriodCurs();
      } else {
        return "You have " + person.getPlayAccount().getAccState().name()
            + " summ = " + person.getPlayAccount().getSumm();
      }

    } catch (NoSuchElementException e){
      return "You are not registration!";
    }
  }

  @Override
  public String sell(String id) {
    try {
    Person person = CheckCursServiceImpl.getPersonByTelegramId(id);
    if (person.getPlayAccount().getAccState() == AccountState.COIN) {
      log.info("Sell FIAT Summ = {} ", buySellService.sellCoin(person));
      return "Sell FIAT Summ = " + person.getPlayAccount().getSumm();
    } else {
      return "You have " + person.getPlayAccount().getAccState().name()
          + " summ = " + person.getPlayAccount().getSumm();
    }
    }catch (NoSuchElementException e) {
      return "You aren't of game";
    }
  }

  @Override
  public String stopGame(String id) {
    try {
      Person person = CheckCursServiceImpl.getPersonByTelegramId(id);
      log.info("Stop Game {} Summ = {} ", person.getPlayAccount().getAccState().name()
          , person.getPlayAccount().getSumm());
      if (CheckCursServiceImpl.stopGame(person)) {
        return "Stop Game " + person.getPlayAccount().getAccState().name()
            + " Summ = " + person.getPlayAccount().getSumm();
      }
    }catch (NoSuchElementException e) {
      return "You aren't of game";
    }
    return "StopGame Problem!";
  }

  @Override
  public String curs(String id) {
    return buySellServiceApi.getCurs()+"";
  }

  @Override
  public String walletInfo(String id) {
    try {
      Person person = registrationPersons.stream()
          .filter(p -> p.getTelegramId().equals(id)).findFirst().orElseThrow();
      return walletInfo(person);
    }catch (NoSuchElementException e) {
      return "You aren't registration!";
    }
  }

  @Override
  public String registration(Person person) {
    //Temporary person
    person = new Person(person.getTelegramId(), summ,
        new PlayAccount(summ, 0, AccountState.FIAT, rangeCursForBuy),
          apiKey,  apiSecret);

    registrationPersons.add(person);
    return "Registration!";
  }

  @Override
  public void stopBot(String id) {
    try {
      Person person = CheckCursServiceImpl.getPersonByTelegramId(id);
      registrationPersons.remove(person);
    }catch (NoSuchElementException e) {
     log.warn(e.getMessage());
    }
  }

  public String walletInfo(Person person) {
    return buySellServiceApi.getWallet(person);
  }

}
