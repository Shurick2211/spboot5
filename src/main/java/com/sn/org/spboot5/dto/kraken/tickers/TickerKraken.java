package com.sn.org.spboot5.dto.kraken.tickers;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class TickerKraken {
 private String [] error;
 private PairResultKraken result;
}