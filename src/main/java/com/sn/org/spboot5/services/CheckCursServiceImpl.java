package com.sn.org.spboot5.services;

import com.sn.org.spboot5.models.Candlestick;
import com.sn.org.spboot5.models.Coin;
import com.sn.org.spboot5.models.Person;
import com.sn.org.spboot5.telegram_bot.Bot;
import com.sn.org.spboot5.utils.AccountState;
import com.sn.org.spboot5.utils.CandlePeriod;
import com.sn.org.spboot5.utils.Trend;
import java.util.HashSet;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CheckCursServiceImpl implements CheckCursService{
  private static final double MIN_KOEF = 1.0025;
  private final BuySellService buySellService;

  private final Candlestick [] candles = new Candlestick[2];

  @Autowired
  private Bot bot;
  @Autowired
  CursFromApi cursFromApi;

  private final static Set<Person> persons = new HashSet<>();

  @Value("${save.game.percent}")
  private double stopPercent;

  @Value("${trend.change.limit.persent}")
  private double changeTrend;

  public CheckCursServiceImpl(BuySellService buySellService) {
    this.buySellService = buySellService;
  }

  @Override
  public void checkCurs(Coin coin) {
    getCandles();
    persons.forEach(person -> playForPerson(person, coin));
  }


  private void playForPerson(Person person, Coin coin){
    savePoint(person, coin);
    if(coin.getTrend() == Trend.UP) {
      if (coin.isChangedTrend() && person.getPlayAccount().getAccState() == AccountState.FIAT) {
        log.info(coin.toString());
        if (isBuy(person) ){
          buy(person);
        }
      }
    } else {
      if (coin.isChangedTrend() && person.getPlayAccount().getAccState() == AccountState.COIN) {
        log.info(coin.toString());
        if (isPrize(person, coin)) {
            sell(person);
        }
      }
    }
  }

  private void buy(Person person){
      log.info("BTC Summ = {} ", buySellService.buyCoin(person));
      bot.sendTelegram(person, "Buying coins BTC = " + person.getPlayAccount().getSumm()
          + "  curs = " + person.getPlayAccount().getStartPeriodCurs());
  }

  private void sell(Person person){
    log.info("Winn FIAT Summ = {} ", buySellService.sellCoin(person));
    person.getPlayAccount().setLastSellTime(cursFromApi.getServerTime());
    savePointPrize(person);
    bot.sendTelegram(person,"Sell coins BTC = " + person.getPlayAccount().getSumm()
        + "  curs = " + person.getPlayAccount().getStartPeriodCurs());
  }

  private void savePoint(Person person, Coin coin) {
    if (candles[0].getTrend() == Trend.DOWN && candles[1].getTrend() == Trend.DOWN
        && person.getPlayAccount().getAccState() == AccountState.COIN
        && person.getStartSummFiat() * MIN_KOEF < person.getPlayAccount().getSumm() * coin.getCurrentCurs()) {
      log.info("Save FIAT Summ = {}", person.getStartSummFiat());
      bot.sendTelegram(person, "Save FIAT Summ = " + person.getStartSummFiat());
      person.setStartSummFiat(buySellService.sellCoin(person));
    }
  }

  private boolean isBuy(Person person) {
    log.info("15m up= {}, 1m up= {}, last candle sell ={}",
        candles[1].getTrend() == Trend.UP,
        candles[0].getTrend() == Trend.UP,
        person.getPlayAccount().getLastSellTime().isBefore(candles[0].getStartTime()));
    return person.getPlayAccount().getLastSellTime().isBefore(candles[0].getStartTime())
        && candles[1].getTrend() == Trend.UP
        && candles[0].getTrend() == Trend.UP;

  }

  private boolean isPrize(Person person, Coin coin) {
    double prizePercent = (coin.getCurrentCurs() - person.getPlayAccount().getStartPeriodCurs())
        / person.getPlayAccount().getStartPeriodCurs() * 100;
    return prizePercent > person.getPlayAccount().getRangePrizeCursInPercent();
  }

  private void savePointPrize(Person person) {
    if (person.getPlayAccount().getAccState() == AccountState.FIAT
        && person.getStartSummFiat() * (1 + stopPercent / 100) < person.getPlayAccount().getSumm()) {
      person.setStartSummFiat(person.getPlayAccount().getSumm());
      log.info("Prize was save, start summ = {}", person.getStartSummFiat());
    }
  }

  private void getCandles(){
    candles[0] = cursFromApi.getCandlesticks(CandlePeriod.MINUTE).get(0);
    candles[1] = cursFromApi.getCandlesticks(CandlePeriod.QUOTER).get(0);
  }

  public static boolean stopGame(Person person) {
      if (persons.remove(person)) {
        log.info("STOP game {}", person);
        return true;
      }
      return false;
  }

  public static void subscribeToCheck(Person person){
    persons.add(person);
  }

  public static Person getPersonByTelegramId(String telegramId) {
    return persons.stream().filter(person -> person.getTelegramId().equals(telegramId)).findFirst().orElseThrow();
  }
}
