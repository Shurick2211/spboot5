package com.sn.org.spboot5.models;

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
public class Person {
  private double startSummFiat;
  private PlayAccount playAccount;
  boolean isPlay;
  private String apiKey;
  private String secretKey;
}
