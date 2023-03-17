package com.sn.org.spboot5.services;

import com.sn.org.spboot5.models.Coin;
import com.sn.org.spboot5.models.Person;
import com.sn.org.spboot5.models.PlayAccount;
import com.sn.org.spboot5.utils.AccountState;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CheckCursServiceImpl implements CheckCursService{
  private final BuySellService buySellService;

  private final List<Person> persons = new ArrayList<>();

  public CheckCursServiceImpl(BuySellService buySellService) {
    this.buySellService = buySellService;
  }

  @Override
  public void checkCurs(Coin coin) {
    log.info(coin.toString());
    persons.forEach(person -> playForPerson(person, coin));
  }

  private void playForPerson(Person person, Coin coin){
    log.info(person.toString());
  }

  public void subscribeToCheck(Person person){
    persons.add(person);
  }
}
