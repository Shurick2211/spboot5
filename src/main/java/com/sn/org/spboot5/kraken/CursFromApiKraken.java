package com.sn.org.spboot5.kraken;

import com.sn.org.spboot5.kraken.dto.tickers.TickerKraken;
import com.sn.org.spboot5.models.Candlestick;
import com.sn.org.spboot5.services.CursFromApi;
import com.sn.org.spboot5.utils.CandlePeriod;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.client.RestTemplate;


@Slf4j
public class CursFromApiKraken implements CursFromApi {

    private final String urlCurs = KrakenApiMethod.TICKER_INFORMATION.getUrl(0) + "?pair=XBTUSD";
    private final RestTemplate template = new RestTemplate();


    @Override
    public double getCurs() {
        TickerKraken response = template.getForEntity(urlCurs,TickerKraken.class).getBody();
        //log.info("Kraken curs BTC/USDT = {}",response.getResult().getPair().getA()[0]);
        return Double.parseDouble(response.getResult().getPair().getA()[0]);
    }

    @Override
    public List<Candlestick> getCandlesticks(CandlePeriod period) {
        return null;
    }


}
