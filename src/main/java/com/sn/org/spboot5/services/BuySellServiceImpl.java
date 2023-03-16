package com.sn.org.spboot5.services;

import com.sn.org.spboot5.models.MyCurrentAccount;
import com.sn.org.spboot5.utils.AccountState;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class BuySellServiceImpl implements BuySellService{
  private MyCurrentAccount currentAccount;
  @Value("${start.summ.fiat}")
  private double summ;

  public BuySellServiceImpl() {
    this.currentAccount = new MyCurrentAccount(summ,AccountState.FIAT);
  }

  @Override
  public double buyCoin(double curs) {
    currentAccount.setAccState(AccountState.COIN);
    currentAccount.setSumm(currentAccount.getSumm()/curs);
    log.info("Buying coins");
    return 0;
  }

  @Override
  public double sellCoin(double curs) {
    currentAccount.setAccState(AccountState.FIAT);
    currentAccount.setSumm(currentAccount.getSumm()*curs);
    log.info("Sell coins");
    return 0;
  }
}
