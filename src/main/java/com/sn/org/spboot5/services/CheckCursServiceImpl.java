package com.sn.org.spboot5.services;

import com.sn.org.spboot5.models.Coin;
import com.sn.org.spboot5.models.Person;
import com.sn.org.spboot5.models.PlayAccount;
import com.sn.org.spboot5.utils.AccountState;
import com.sn.org.spboot5.utils.Trend;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
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
        if (isMoreRange(person, coin)){
          buySellService.buyCoin(coin, person);
        }
      }
    } else {
      if (coin.isChangedTrend() && isMoreRange(person, coin)){
          buySellService.sellCoin(coin, person);
      }
    }

  }

  private boolean isMoreRange(Person person, Coin coin) {
    //double prizePercent = person.getPlayAccount().
    return true;
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
