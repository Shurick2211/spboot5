package com.sn.org.spboot5.services;

import com.sn.org.spboot5.models.Coin;
import com.sn.org.spboot5.utils.RunAfterStartUp;
import com.sn.org.spboot5.utils.Trend;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CurseService {
  private final Coin coin;
  private final RunAfterStartUp curs;
  private final CheckCursService checkCursService;

  @Value("${range.curs.for.buy}")
  private double rangeCursForBuy;

  public CurseService(RunAfterStartUp curs, CheckCursService checkCursService) {
    this.curs = curs;
    this.checkCursService = checkCursService;
    double startCurs = curs.getCurs();
    this.coin = new Coin(startCurs,startCurs,Trend.UP);
  }

  @Scheduled(fixedDelayString = "${freq.req.curs}")
  public void seenCurs(){
    coin.setCurrentCurs(curs.getCurs());
    coin.setTrend(coin.getCurrentCurs() >= coin.getLastCurs() ? Trend.UP: Trend.DOWN);
    checkCursService.checkCurs(coin);
    //log.info("Trend = {}, BTC = {}", coin.getTrend(), coin.getCurrentCurs());
    coin.setLastCurs(coin.getCurrentCurs());
  }

}
