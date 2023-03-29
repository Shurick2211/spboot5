package com.sn.org.spboot5.services;

import com.sn.org.spboot5.models.Coin;
import com.sn.org.spboot5.models.Person;
import com.sn.org.spboot5.telegram_bot.Bot;
import com.sn.org.spboot5.utils.AccountState;
import com.sn.org.spboot5.utils.Trend;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CheckCursServiceImpl implements CheckCursService{
  private static final double MIN_KOEF = 1.002;
  private final BuySellService buySellService;
  @Autowired
  private Bot bot;

  private final static List<Person> persons = new ArrayList<>();

  @Value("${stop.game.percent}")
  private double stopPercent;

  @Value("${trend.change.limit.persent}")
  private double changeTrend;

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
          bot.sendTelegram(person,"Buying coins BTC = " + person.getPlayAccount().getSumm() + "  curs = " + coin.getCurrentCurs());
        }
      }
    } else {
      if (coin.isChangedTrend()) {
        log.info(coin.toString());
        if (person.getPlayAccount().getAccState() == AccountState.COIN) {
          if (isPrize(person, coin)) {
            log.info("Winn FIAT Summ = {} ", buySellService.sellCoin(coin, person));
            bot.sendTelegram(person,"Sell coins BTC = " + person.getPlayAccount().getSumm() + "  curs = " + coin.getCurrentCurs());
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
      bot.sendTelegram(person, "Save FIAT Summ = {}" + person.getStartSummFiat());
      person.setStartSummFiat(buySellService.sellCoin(coin, person));
     // person.setPlay(false);
    } else {
      log.info("WAIT...");
      bot.sendTelegram(person, "Wait...");
    }
  }

  private boolean isNoBad(Person person, Coin coin) {
    double trendDown = person.getPlayAccount().getStartPeriodCurs()
        * (1 - 2 * changeTrend)/100;
   // double trendUP = person.getPlayAccount().getStartPeriodCurs() * (1 + changeTrend)/100;
    return trendDown > coin.getCurrentCurs(); //|| trendUP < coin.getCurrentCurs();
  }

  private boolean isPrize(Person person, Coin coin) {
    double prizePercent = (coin.getCurrentCurs() - person.getPlayAccount().getStartPeriodCurs())
        / person.getPlayAccount().getStartPeriodCurs() * 100;
    return prizePercent > person.getPlayAccount().getRangePrizeCursInPercent();
  }

  private void newPerson(Person person, Coin coin) {
    if (!person.isPlay()) {
      log.info("New player - {}", person);
      bot.sendTelegram(person, "New player - " + person);
      buySellService.buyCoin(coin, person);
      person.setPlay(true);
    }
  }

  private void stopGame(Person person) {
    if (person.getPlayAccount().getAccState() == AccountState.FIAT
        && person.getStartSummFiat() * (1 + stopPercent / 100) < person.getPlayAccount().getSumm()) {
      if (persons.remove(person)) {
        person.setStartSummFiat(person.getPlayAccount().getSumm());
        person.setPlay(false);
        log.info("STOP game {}", person);
        bot.sendTelegram(person, "STOP game {}" + person);
      }
    }
  }

  public static void subscribeToCheck(Person person){
    persons.add(person);
  }
}
