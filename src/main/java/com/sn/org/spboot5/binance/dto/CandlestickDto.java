package com.sn.org.spboot5.binance.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CandlestickDto {
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
