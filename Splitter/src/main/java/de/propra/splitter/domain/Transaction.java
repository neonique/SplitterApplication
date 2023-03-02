package de.propra.splitter.domain;

import java.util.Set;
import org.javamoney.moneta.Money;
import org.springframework.lang.NonNull;

public record Transaction(@NonNull User sponsor,@NonNull Set<User> beggars, @NonNull Money money) {

  public boolean isBeggar(User user) {
    return beggars.contains(user);
  }

  public boolean isSponsor(User user) {
    return user.equals(sponsor);
  }

  public boolean isParticipant (User user) {
    return isSponsor(user)|| isBeggar(user);
  }
  public int countBeggars(){
    return beggars.size();
  }
}
