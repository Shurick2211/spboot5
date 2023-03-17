package com.sn.org.spboot5.services;

import com.sn.org.spboot5.models.Coin;
import com.sn.org.spboot5.models.Person;

public interface CheckCursService {
  void checkCurs(Coin coin);
  void subscribeToCheck(Person person);
}
