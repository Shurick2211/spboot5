package com.sn.org.spboot5.binance;

import com.sn.org.spboot5.binance.dto.CandlestickDto;
import com.sn.org.spboot5.models.Candlestick;
import com.sn.org.spboot5.utils.CandlePeriod;
import com.sn.org.spboot5.utils.Trend;

public class DtoToCandlestick {

  public static Candlestick getCandlestick(CandlestickDto dto, String symbol, CandlePeriod period){
    Candlestick candlestick = new Candlestick();
    candlestick.setPair(symbol);
    candlestick.setOpenPrice(dto.getOpenPrice());
    candlestick.setClosePrice(dto.getClosePrice());
    candlestick.setLowPrice(dto.getLowPrice());
    candlestick.setHighPrice(dto.getHighPrice());
    candlestick.setPeriod(period);
    candlestick.setTrend(
        Double.parseDouble(dto.getClosePrice()) > Double.parseDouble(dto.getOpenPrice())
            ? Trend.UP : Trend.DOWN);
    return candlestick;
  }

}
