package com.sn.org.spboot5.services;

import com.sn.org.spboot5.models.Coin;
import com.sn.org.spboot5.models.Person;
import com.sn.org.spboot5.utils.AccountState;
import com.sn.org.spboot5.utils.Trend;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j ;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CheckCursServiceImpl implements CheckCursService{
  private static final double MIN_KOEF = 1.002;
  private final BuySellService buySellService;

  private final List<Person> persons = new ArrayList<>();

  @Value("${stop.game.percent}")
  private double stopPercent;


  public CheckCursServiceImpl(BuySellService buySellService) {
    this.buySellService = buySellService;

  }

  @Override
  public void checkCurs(Coin coin) {
    persons.forEach(person -> playForPerson(person, coin));
  }


  private void playForPerson(Person person, Coin coin){
    newPerson(person, coin);
    if(coin.getTrend() == Trend.UP) {
      if (coin.isChangedTrend()) {
        log.info(coin.toString());
        if (isNoBad(person, coin) && person.getPlayAccount().getAccState() == AccountState.FIAT){
          log.info("BTC Summ = {} ", buySellService.buyCoin(coin, person));
        }
      }
    } else {
      if (coin.isChangedTrend()) {
        log.info(coin.toString());
        if (person.getPlayAccount().getAccState() == AccountState.COIN) {
          if (isPrize(person, coin)) {
            log.info("Winn FIAT Summ = {} ", buySellService.sellCoin(coin, person));
            stopGame(person);
          } else {
            savePoint(person, coin);
          }
        }
      }
    }
  }

  private void savePoint(Person person, Coin coin) {
    if (person.getPlayAccount().getRangePrizeCursInPercent() < (coin.getLastCurs()/coin.getCurrentCurs() - 1)
        && person.getStartSummFiat() * MIN_KOEF < person.getPlayAccount().getSumm() * coin.getCurrentCurs()) {
      log.info("Save FIAT Summ = {}", person.getStartSummFiat());
      person.setStartSummFiat(buySellService.sellCoin(coin, person));
     // person.setPlay(false);
    } else {
      log.info("WAIT...");
    }
  }

  private boolean isNoBad(Person person, Coin coin) {
    double savePrize = person.getPlayAccount().getStartPeriodCurs()
        * (1 - person.getPlayAccount().getRangePrizeCursInPercent())/100;
    return savePrize > coin.getCurrentCurs();
  }

  private boolean isPrize(Person person, Coin coin) {
    double prizePercent = (coin.getCurrentCurs() - person.getPlayAccount().getStartPeriodCurs())
        / person.getPlayAccount().getStartPeriodCurs() * 100;
    return prizePercent > person.getPlayAccount().getRangePrizeCursInPercent();
  }

  private void newPerson(Person person, Coin coin) {
    if (!person.isPlay()) {
      log.info("New player - {}", person);
      buySellService.buyCoin(coin, person);
      person.setPlay(true);
    }
  }

  private void stopGame(Person person) {
    if (person.getPlayAccount().getAccState() == AccountState.FIAT
        && person.getStartSummFiat() * (1 + stopPercent / 100) > person.getPlayAccount().getSumm()) {
      if (persons.remove(person)) {
        person.setPlay(false);
        log.info("STOP game {}", person);
      }
    }
  }

  public void subscribeToCheck(Person person){
    persons.add(person);
  }
}
