package com.sn.org.spboot5.services;

import com.sn.org.spboot5.models.Coin;
import com.sn.org.spboot5.models.Person;

public interface BuySellServiceApi {
  double buyCoin(Coin coin, Person person);
  double sellCoin(Coin coin, Person person);

}
