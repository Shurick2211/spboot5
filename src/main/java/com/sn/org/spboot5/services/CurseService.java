package com.sn.org.spboot5.services;

import com.sn.org.spboot5.models.Coin;
import com.sn.org.spboot5.utils.RunAfterStartUp;
import com.sn.org.spboot5.utils.Trend;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CurseService {
  private final Coin coin;

  private final CursFromApi cursFromApi;
  private final CheckCursService checkCursService;

  private Trend lastTrend = Trend.UP;


  public CurseService(CursFromApi cursFromApi, CheckCursService checkCursService) {
    this.cursFromApi = cursFromApi;
    this.checkCursService = checkCursService;
    double startCurs = cursFromApi.getCurs();
    this.coin = new Coin(startCurs, startCurs, lastTrend, false);
  }

  @Scheduled(fixedDelayString = "${freq.req.curs}")
  public void seenCurs(){
    coin.setCurrentCurs(cursFromApi.getCurs());
    coin.setTrend(coin.getCurrentCurs() >= coin.getLastCurs() ? Trend.UP: Trend.DOWN);
    coin.setChangedTrend(coin.getTrend() != lastTrend);
    checkCursService.checkCurs(coin);
    coin.setLastCurs(coin.getCurrentCurs());
    lastTrend = coin.getTrend();
  }


}
