package com.sn.org.spboot5.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class RunAfterStartUp  {

  @EventListener(ApplicationReadyEvent.class)
  public void runAfterStartup() {
    log.info("START!");
  }
}