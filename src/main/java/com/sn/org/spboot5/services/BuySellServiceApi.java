package com.sn.org.spboot5.services;

import com.sn.org.spboot5.models.Person;

public interface BuySellServiceApi extends CursFromApi{
  double buyCoin(Person person);
  double sellCoin(Person person);

  String getWallet(Person person);

}
