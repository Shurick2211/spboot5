package com.sn.org.spboot5.services;

public interface TelegramBotCommandListener {
  String buy(String id);
  String sell(String id);
  String curs(String id);
  String walletInfo(String id);
}
