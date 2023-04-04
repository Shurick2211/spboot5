package com.sn.org.spboot5.utils;

public enum CandlePeriod {
  QUOTER("15m"),
  HOUR("1h"),
  DAY("1d");

  private final String period;
  CandlePeriod(String period) {
    this.period = period;
  }
  public String getPeriod() {
    return period;
  }
}
