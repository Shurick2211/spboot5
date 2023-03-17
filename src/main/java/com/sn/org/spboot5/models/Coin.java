package com.sn.org.spboot5.models;

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
public class Coin {
  private double lastCurs;
  private double currentCurs;
  private Trend trend;
  private boolean isChangedTrend;
}
