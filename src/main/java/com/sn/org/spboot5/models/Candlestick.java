package com.sn.org.spboot5.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Candlestick {
  private long openTimeMills;
  private String openPrice;
  private String highPrice;
  private String lowPrice;
  private String closePrice;
  private String volume;
  private long closeTimeMills;
  private String quoteAssetVolume;
  private int numberOfTrades;
  private String takerBuyBaseAssetVolume;
  private String takerBuyQuoteAssetVolume;
  private String unusedFieldIgnore;

}
