package com.sn.org.spboot5.models;

import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Person {
  private String telegramId;
  private double startSummFiat;
  private PlayAccount playAccount;
  private String apiKey;
  private String secretKey;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Person person = (Person) o;
    return telegramId.equals(person.telegramId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(telegramId);
  }
}
