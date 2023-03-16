package com.sn.org.spboot5.services;

import com.sn.org.spboot5.models.Coin;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CheckCursServiceImpl implements CheckCursService{
  @Value("${range.curs.for.buy}")
  private double rangeCursForBuy;

  @Override
  public void checkCurs(Coin coin) {
    log.info(coin.toString());
  }
}
