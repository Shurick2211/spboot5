package com.sn.org.spboot5.binance;


import com.binance.connector.client.SpotClient;
import com.binance.connector.client.impl.SpotClientImpl;
import com.binance.connector.client.utils.JSONParser;
import com.sn.org.spboot5.services.CursFromApi;
import java.util.LinkedHashMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class BinanceApi implements CursFromApi {

  private final SpotClient client = new SpotClientImpl();

  LinkedHashMap<String,Object> parameters = new LinkedHashMap<>();


  @Override
  public double getCurs() {
    parameters.put("symbol","BTCUSDT");
    //log.info(client.createMarket().ticker(parameters));
    return Double.parseDouble(JSONParser.getJSONStringValue(client.createMarket().ticker(parameters),"lastPrice"));
  }
}
