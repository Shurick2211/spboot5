package com.sn.org.spboot5.models;

import com.sn.org.spboot5.utils.CandlePeriod;
import com.sn.org.spboot5.utils.Trend;
import java.time.LocalDateTime;
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
  private double openPrice;
  private double highPrice;
  private double lowPrice;
  private double closePrice;
  private Trend trend;
  private CandlePeriod period;
  private LocalDateTime startTime;
  private LocalDateTime endTime;

}
