package com.sn.org.spboot5.services;

import com.sn.org.spboot5.models.Coin;
import com.sn.org.spboot5.models.Person;
import com.sn.org.spboot5.utils.Trend;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j ;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CheckCursServiceImpl implements CheckCursService{
  private final BuySellService buySellService;

  private final List<Person> persons = new ArrayList<>();


  public CheckCursServiceImpl(BuySellService buySellService) {
    this.buySellService = buySellService;
  }

  @Override
  public void checkCurs(Coin coin) {
    log.info(coin.toString());
    persons.forEach(person -> playForPerson(person, coin));
  }



  private void playForPerson(Person person, Coin coin){
    if(coin.getTrend() == Trend.UP) {
      if (coin.isChangedTrend()) {
        newPerson(person, coin);
        if (isNoBad(person, coin)){
          log.info("BTC Summ = {}", buySellService.buyCoin(coin, person));
        }
      }
    } else {
      if (coin.isChangedTrend()) {
        if (isPrize(person, coin)) {
          log.info("FIAT Summ = {}", buySellService.sellCoin(coin, person));
        } else {
          savePoint(person, coin);
        }
      }
    }

  }

  private void savePoint(Person person, Coin coin) {
    if (person.getStartSummFiat() < person.getPlayAccount().getSumm() * coin.getCurrentCurs()) {
      person.setStartSummFiat(buySellService.sellCoin(coin, person));
      person.setPlay(false);
      log.info("Save FIAT Summ = {}", person.getStartSummFiat());
    } else {
      log.info("WAIT...");
    }
  }

  private boolean isNoBad(Person person, Coin coin) {
    double savePrize = person.getPlayAccount().getStartPeriodCurs()
        * (1 + person.getPlayAccount().getRangePrizeCursInPercent())/100;
    return savePrize < coin.getCurrentCurs();
  }

  private boolean isPrize(Person person, Coin coin) {
    double prizePercent = (coin.getCurrentCurs() - person.getPlayAccount().getStartPeriodCurs())
        / person.getPlayAccount().getStartPeriodCurs() * 100;
    return prizePercent > person.getPlayAccount().getRangePrizeCursInPercent();
  }

  private void newPerson(Person person, Coin coin) {
    if (!person.isPlay()) {
      buySellService.buyCoin(coin, person);
      person.setPlay(true);
    }
  }

  public void subscribeToCheck(Person person){
    persons.add(person);
  }
}
