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
public class MyCurrentAccount {
  private double summ;
  private AccountState accState;

}
