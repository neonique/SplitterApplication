package de.propra.splitter.domain;

import java.util.Set;
import javax.money.MonetaryAmount;
import org.javamoney.moneta.Money;
import org.springframework.lang.NonNull;

public record Transaction(@NonNull User sponsor,@NonNull Set<User> beggers, @NonNull Money money) {

  public boolean isBegger(User user) {
    return beggers.contains(user);
  }

  public boolean isSponsor(User user) {
    return user.equals(sponsor);
  }

  public boolean isParticipant (User user) {
    return isSponsor(user)||isBegger(user);
  }

}
