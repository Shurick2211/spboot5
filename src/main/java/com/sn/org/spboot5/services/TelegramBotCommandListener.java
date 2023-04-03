package com.sn.org.spboot5.services;

import com.sn.org.spboot5.models.Person;

public interface TelegramBotCommandListener {
  String buy(String id);
  String sell(String id);
  String stopGame(String id);
  String curs(String id);
  String walletInfo(String id);
  String registration(Person person);
}
