package com.sn.org.spboot5.models;

import com.sn.org.spboot5.utils.AccountState;
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
public class PlayAccount {
  private double summ;
  private double startPeriodCurs;
  private AccountState accState;
  private double rangeCursForBuying;

}
