package com.sn.org.spboot5.services;

import com.sn.org.spboot5.utils.RunAfterStartUp;
import com.sn.org.spboot5.utils.Trend;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CurseService {
  private Trend trend;
  private double lastCurs;
  private double currentCurs;
  private final RunAfterStartUp curs;

  public CurseService(RunAfterStartUp curs) {
    this.trend = Trend.UP;
    this.curs = curs;
    this.lastCurs = curs.getCurs();
  }

  @Scheduled(fixedDelay = 2000)
  public void seenCurs(){
    currentCurs = curs.getCurs();
    trend = currentCurs >= lastCurs ? Trend.UP: Trend.DOWN;
    log.info("Trend = {}, BTC = {}", trend, currentCurs);
    lastCurs = currentCurs;
  }

}
