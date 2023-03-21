package com.sn.org.spboot5.services;

import com.sn.org.spboot5.models.Coin;
import com.sn.org.spboot5.utils.Trend;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CurseService {
  private final Coin coin;

  private final CursFromApi cursFromApi;
  private final CheckCursService checkCursService;

  private Trend lastTrend = Trend.UP;

  @Value("${trend.change}")
  private double changeTrend;


  public CurseService(CursFromApi cursFromApi, CheckCursService checkCursService) {
    this.cursFromApi = cursFromApi;
    this.checkCursService = checkCursService;
    double startCurs = cursFromApi.getCurs();
    this.coin = new Coin(startCurs, startCurs, 0, lastTrend, false);
  }

  @Scheduled(fixedDelayString = "${freq.req.curs}")
  public void seenCurs(){
    coin.setCurrentCurs(cursFromApi.getCurs());
    if (coin.getCurrentCurs() != coin.getLastCurs()) {
      coin.setRate(changeTrendPercent(coin.getCurrentCurs(), coin.getLastCurs()));
      coin.setTrend(coin.getCurrentCurs() > coin.getLastCurs() ? Trend.UP : Trend.DOWN);
    }
    coin.setChangedTrend(coin.getTrend() != lastTrend);
    //processing
    checkCursService.checkCurs(coin);
    //
    if (coin.getRate() > changeTrend) {
      coin.setLastCurs(coin.getCurrentCurs());
    }
    lastTrend = coin.getTrend();
  }

  private double changeTrendPercent(double currentCurs, double lastCurs) {
    return lastCurs/currentCurs > 1 ? lastCurs/currentCurs - 1: 1 - lastCurs/currentCurs;
  }


}
