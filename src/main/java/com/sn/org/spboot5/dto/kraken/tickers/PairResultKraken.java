package com.sn.org.spboot5.dto.kraken.tickers;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class PairResultKraken {
    @JsonProperty(value="XXBTZUSD")
    PairKraken pair;
}
