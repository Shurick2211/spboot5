package com.sn.org.spboot5.models;

import com.sn.org.spboot5.utils.CandlePeriod;
import com.sn.org.spboot5.utils.Trend;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Candlestick {
  private String pair;
  private String openPrice;
  private String highPrice;
  private String lowPrice;
  private String closePrice;
  private Trend trend;
  private CandlePeriod period;

}
