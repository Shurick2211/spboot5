package com.sn.org.spboot5.services;

import com.sn.org.spboot5.models.Coin;
import com.sn.org.spboot5.models.Person;
import com.sn.org.spboot5.telegram_bot.Bot;
import com.sn.org.spboot5.utils.AccountState;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.message.StringFormattedMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class BuySellService {

  private final BuySellServiceApi buySellServiceApi;
  @Autowired
  public BuySellService(BuySellServiceApi buySellServiceApi) {
    this.buySellServiceApi = buySellServiceApi;
  }

  public double buyCoin(Coin coin, Person person) {
    if (person.getPlayAccount().getAccState() == AccountState.FIAT) {
      person.getPlayAccount().setAccState(AccountState.COIN);
      //buySellServiceApi.buyCoin(person);
      person.getPlayAccount().setStartPeriodCurs(coin.getCurrentCurs());
      person.getPlayAccount().setSumm(person.getPlayAccount().getSumm() / coin.getCurrentCurs());
      log.info("Buying coins BTC = {}  curs = {}", person.getPlayAccount().getSumm(), coin.getCurrentCurs());
    }
    return person.getPlayAccount().getSumm();
  }


  public double sellCoin(Coin coin, Person person) {
    if (person.getPlayAccount().getAccState() == AccountState.COIN) {
      person.getPlayAccount().setAccState(AccountState.FIAT);
      //buySellServiceApi.sellCoin(person);
      person.getPlayAccount().setStartPeriodCurs(coin.getCurrentCurs());
      person.getPlayAccount().setSumm(person.getPlayAccount().getSumm() * coin.getCurrentCurs());
      log.info("Sell coins FIAT = {}  curs = {}", person.getPlayAccount().getSumm(), coin.getCurrentCurs());
    }
    return person.getPlayAccount().getSumm();
  }

}
