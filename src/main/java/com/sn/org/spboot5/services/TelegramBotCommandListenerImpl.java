package com.sn.org.spboot5.services;

import com.sn.org.spboot5.models.Person;
import com.sn.org.spboot5.models.PlayAccount;
import com.sn.org.spboot5.utils.AccountState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class TelegramBotCommandListenerImpl implements TelegramBotCommandListener {
  @Value("${start.summ.fiat}")
  private double summ;
  @Value("${range.prize.curs.percent}")
  private double rangeCursForBuy;

  @Autowired
  private BuySellServiceApi buySellServiceApi;
  @Autowired
  private CursFromApi cursFromApi;
  @Autowired
  private CheckCursService checkCursService;
  @Override
  public String buy(String id) {
    checkCursService.subscribeToCheck(new Person(id,summ,
        new PlayAccount(summ, 0, AccountState.FIAT, rangeCursForBuy),
        false,
        " ",
        " "));
    return "Tyt buy";
  }

  @Override
  public String sell(String id) {
    return "Tyt sell";
  }

  @Override
  public String curs(String id) {
    return cursFromApi.getCurs()+"";
  }

  @Override
  public String accountInfo(String id) {
    return "bla-bla";
  }


}
