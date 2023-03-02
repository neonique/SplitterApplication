package de.propra.splitter.domain;

import java.util.Objects;
import java.util.Set;
import org.javamoney.moneta.Money;
import org.springframework.lang.NonNull;

public final class Transaction {

  @NonNull
  private final User sponsor;
  @NonNull
  private final Set<User> beggars;
  @NonNull
  private final Money money;
  @NonNull
  private final String description;

  public Transaction(@NonNull User sponsor, @NonNull Set<User> beggars, @NonNull Money money,
      @NonNull String description) {
    if(beggars.isEmpty()) {
      throw new IllegalArgumentException("transactions must have beggars");
    }
    if(beggars.size() == 1 && beggars.contains(sponsor)) {
      throw new IllegalArgumentException("transactions need more than one participant");
    }
    this.sponsor = sponsor;
    this.beggars = beggars;
    this.money = money;
    this.description = description;
  }

  public boolean isBeggar(User user) {
    return beggars.contains(user);
  }

  public boolean isSponsor(User user) {
    return user.equals(sponsor);
  }

  public boolean isParticipant(User user) {
    return isSponsor(user) || isBeggar(user);
  }

  public int countBeggars() {
    return beggars.size();
  }

  @NonNull
  public User sponsor() {
    return sponsor;
  }

  @NonNull
  public Set<User> beggars() {
    return beggars;
  }

  @NonNull
  public Money money() {
    return money;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this)
      return true;
    if (obj == null || obj.getClass() != this.getClass())
      return false;
    var that = (Transaction) obj;
    return Objects.equals(this.sponsor, that.sponsor) &&
        Objects.equals(this.beggars, that.beggars) &&
        Objects.equals(this.money, that.money);
  }

  @Override
  public int hashCode() {
    return Objects.hash(sponsor, beggars, money);
  }

  @Override
  public String toString() {
    return "Transaction[" +
        "sponsor=" + sponsor + ", " +
        "beggars=" + beggars + ", " +
        "money=" + money + ']';
  }


}
