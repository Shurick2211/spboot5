package com.sn.org.spboot5.kraken.dto.balance;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class AccountBalance {
  private Asset result;
  private String [] error;

}
