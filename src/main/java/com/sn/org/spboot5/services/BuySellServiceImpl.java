package com.sn.org.spboot5.services;

import com.sn.org.spboot5.models.Coin;
import com.sn.org.spboot5.models.Person;
import com.sn.org.spboot5.utils.AccountState;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class BuySellServiceImpl implements BuySellService{


  @Override
  public double buyCoin(Coin coin, Person person) {
    if (person.getPlayAccount().getAccState() == AccountState.FIAT) {
      person.getPlayAccount().setAccState(AccountState.COIN);
      person.getPlayAccount().setStartPeriodCurs(coin.getCurrentCurs());
      person.getPlayAccount().setSumm(person.getPlayAccount().getSumm() / coin.getCurrentCurs());
      log.info("Buying coins BTC = {}", person.getPlayAccount().getSumm());
    }
    return person.getPlayAccount().getSumm();
  }

  @Override
  public double sellCoin(Coin coin, Person person) {
    if (person.getPlayAccount().getAccState() == AccountState.COIN) {
      person.getPlayAccount().setAccState(AccountState.FIAT);
      person.getPlayAccount().setStartPeriodCurs(coin.getCurrentCurs());
      person.getPlayAccount().setSumm(person.getPlayAccount().getSumm() * coin.getCurrentCurs());
      log.info("Sell coins FIAT = {}", person.getPlayAccount().getSumm());
    }
    return person.getPlayAccount().getSumm();
  }

}
