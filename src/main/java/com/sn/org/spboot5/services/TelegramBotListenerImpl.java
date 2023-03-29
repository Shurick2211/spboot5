package com.sn.org.spboot5.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TelegramBotListenerImpl implements TelegramBotListener{

  @Autowired
  private BuySellServiceApi buySellServiceApi;
  @Autowired
  private CursFromApi cursFromApi;
  @Override
  public String buy() {
    return null;
  }

  @Override
  public String sell() {
    return null;
  }

  @Override
  public String curs() {
    return cursFromApi.getCurs()+"";
  }

  @Override
  public String accountInfo() {
    return null;
  }
}
