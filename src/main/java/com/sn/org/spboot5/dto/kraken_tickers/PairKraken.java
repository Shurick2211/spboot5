package com.sn.org.spboot5.dto.kraken_tickers;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class PairKraken {
    private String [] a;
    private String [] b;
    private String [] c;
    private String [] v;
    private String [] p;
    private Integer [] t;
    private String [] l;
    private String [] h;
    private String o;
}
