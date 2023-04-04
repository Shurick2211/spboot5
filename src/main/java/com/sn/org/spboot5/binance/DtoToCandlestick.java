package com.sn.org.spboot5.binance;


import com.sn.org.spboot5.models.Candlestick;
import com.sn.org.spboot5.utils.CandlePeriod;
import com.sn.org.spboot5.utils.Trend;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import org.json.JSONArray;

public class DtoToCandlestick {

  public static Candlestick getCandlestick(JSONArray dto, String symbol, CandlePeriod period){
    Candlestick candlestick = new Candlestick();
    candlestick.setPair(symbol);
    candlestick.setOpenPrice(Double.parseDouble(dto.getString(1)));
    candlestick.setClosePrice(Double.parseDouble(dto.getString(4)));
    candlestick.setLowPrice(Double.parseDouble(dto.getString(3)));
    candlestick.setHighPrice(Double.parseDouble(dto.getString(2)));
    candlestick.setPeriod(period);
    candlestick.setTrend(candlestick.getClosePrice() > candlestick.getOpenPrice()
            ? Trend.UP : Trend.DOWN);
    candlestick.setStartTime(
        LocalDateTime.ofInstant(Instant.ofEpochMilli((Long) dto.get(0)), ZoneId.of("UTC")));
    candlestick.setEndTime(
        LocalDateTime.ofInstant(Instant.ofEpochMilli((Long) dto.get(6)), ZoneId.of("UTC")));
    return candlestick;
  }
}
