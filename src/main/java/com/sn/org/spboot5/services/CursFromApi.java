package com.sn.org.spboot5.services;

import com.sn.org.spboot5.models.Candlestick;
import com.sn.org.spboot5.utils.CandlePeriod;
import java.util.List;

public interface CursFromApi {
  double getCurs();
  List<Candlestick> getCandlesticks(CandlePeriod period);

}
