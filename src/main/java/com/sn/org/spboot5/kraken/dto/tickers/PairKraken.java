package com.sn.org.spboot5.kraken.dto.tickers;

import lombok.*;
/**
        a
        Array of strings
        Ask [<price>, <whole lot volume>, <lot volume>]

        b
        Array of strings
        Bid [<price>, <whole lot volume>, <lot volume>]

        c
        Array of strings
        Last trade closed [<price>, <lot volume>]

        v
        Array of strings
        Volume [<today>, <last 24 hours>]

        p
        Array of strings
        Volume weighted average price [<today>, <last 24 hours>]

        t
        Array of integers
        Number of trades [<today>, <last 24 hours>]

        l
        Array of strings
        Low [<today>, <last 24 hours>]

        h
        Array of strings
        High [<today>, <last 24 hours>]

        o
        string
        Today's opening price
 **/
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
