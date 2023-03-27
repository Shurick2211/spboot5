package com.sn.org.spboot5.binance;


import com.binance.connector.client.SpotClient;
import com.binance.connector.client.impl.SpotClientImpl;
import com.binance.connector.client.utils.JSONParser;
import com.sn.org.spboot5.models.Person;
import com.sn.org.spboot5.services.BuySellServiceApi;
import com.sn.org.spboot5.services.CursFromApi;
import java.util.LinkedHashMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class BinanceApi implements CursFromApi, BuySellServiceApi {

  private final SpotClient clientForCurs = new SpotClientImpl();

  LinkedHashMap<String,Object> parameters = new LinkedHashMap<>();


  @Override
  public double getCurs() {
    parameters.put("symbol","BTCUSDT");
    return Double.parseDouble(JSONParser.getJSONStringValue(clientForCurs.createMarket().tickerSymbol(parameters),"price"));
  }

  @Override
  public double buyCoin(Person person) {
    parameters.put("side", "BUY");
    return getOrder(person);
  }

  @Override
  public double sellCoin(Person person) {
    parameters.put("side", "SELL");
    return getOrder(person);
  }

  private double getOrder(Person person){
    parameters.put("type", "MARKET");
    parameters.put("quoteOrderQty", person.getPlayAccount().getSumm());
    SpotClient clientPerson = new SpotClientImpl(person.getApiKey(), person.getSecretKey());
    long orderId = Long.parseLong(JSONParser.getJSONStringValue(clientPerson.createTrade().newOrder(parameters),"orderId"));
    parameters.put("orderId", orderId);
    String order = clientPerson.createTrade().getOrder(parameters);
    person.getPlayAccount().setSumm(Double.parseDouble(JSONParser.getJSONStringValue(order, "executedQty")));
    person.getPlayAccount().setStartPeriodCurs(Double.parseDouble(JSONParser.getJSONStringValue(order, "price")));
    return Double.parseDouble(JSONParser.getJSONStringValue(order, "executedQty"));
  }
}
