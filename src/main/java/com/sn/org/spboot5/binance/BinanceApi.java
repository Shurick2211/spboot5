package com.sn.org.spboot5.binance;


import com.binance.connector.client.SpotClient;
import com.binance.connector.client.impl.SpotClientImpl;
import com.binance.connector.client.utils.JSONParser;
import com.sn.org.spboot5.models.Candlestick;
import com.sn.org.spboot5.models.Person;
import com.sn.org.spboot5.services.BuySellServiceApi;
import com.sn.org.spboot5.utils.CandlePeriod;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class BinanceApi implements BuySellServiceApi {

  public static final String PAIR = "BTCUSDT";
  private final SpotClient clientForCurs = new SpotClientImpl();

  LinkedHashMap<String,Object> parameters = new LinkedHashMap<>();


  @Override
  public double getCurs() {
    clearsParam();
    return Double.parseDouble(JSONParser.getJSONStringValue(clientForCurs.createMarket().tickerSymbol(parameters),"price"));
  }

  @Override
  public List<Candlestick> getCandlesticks(CandlePeriod period) {
    clearsParam();
    parameters.put("interval", period.getPeriod());
    parameters.put("limit", 5);
    String jsonArray = clientForCurs.createMarket().klines(parameters);
    JSONArray array = new JSONArray(jsonArray);
    List<Candlestick> candlesticks = new ArrayList<>();
    for (Object c:array){
      JSONArray arrayC = new JSONArray(c.toString());
      candlesticks.add(DtoToCandlestick.getCandlestick(arrayC,parameters.get("symbol").toString(),period));
    }

    return candlesticks;
  }

  @Override
  public LocalDateTime getServerTime() {
    return  LocalDateTime.ofInstant(Instant.ofEpochMilli(Long.parseLong(
        clientForCurs.createMarket().time().substring(14,27))), ZoneId.of("UTC"));
  }

  @Override
  public double buyCoin(Person person) {
    clearsParam();
    parameters.put("side", "BUY");
    return getOrder(person);
  }

  @Override
  public double sellCoin(Person person) {
    clearsParam();
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

  @Override
  public  String getWallet(Person person) {
    clearsParam();
    SpotClient clientPerson = new SpotClientImpl(person.getApiKey(), person.getSecretKey());
    parameters.put("type","SPOT");
    String res = clientPerson.createWallet().fundingWallet(parameters);
    JSONArray results = new JSONArray(res);
    res = "";
    for (Object r: results) {
      log.info("{} = {}", JSONParser.getJSONStringValue( r.toString(), "asset"),
          JSONParser.getJSONStringValue( r.toString(), "free"));
      res += JSONParser.getJSONStringValue( r.toString(), "asset")
          + " = "
          + JSONParser.getJSONStringValue( r.toString(), "free")
          + "\n";
    }
    return res;
  }

  private void clearsParam() {
    parameters.clear();
    parameters.put("symbol", PAIR);
  }
}
