package com.sn.org.spboot5.services;

import com.sn.org.spboot5.dto.kraken_tickers.TickerKraken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class CursFromApiKraken implements CursFromApi{
    String url = "https://api.kraken.com/0/public/Ticker?pair=XBTUSD";
    RestTemplate template = new RestTemplate();


    @Override
    public double getCurs() {
        TickerKraken response = template.getForEntity(url,TickerKraken.class).getBody();
        //log.info("Kraken curs BTC/USDT = {}",response.getResult().getPair().getA()[0]);
        return Double.parseDouble(response.getResult().getPair().getA()[0]);
    }
}
