package de.propra.splitter.domain;

import java.util.Objects;
import java.util.Set;
import org.javamoney.moneta.Money;
import org.springframework.lang.NonNull;

final class Transaktion {

  @NonNull
  private final Nutzer sponsor;
  @NonNull
  private final Set<Nutzer> bettler;
  @NonNull
  private final Money betrag;
  @NonNull
  private final String beschreibung;

  Transaktion(@NonNull Nutzer sponsor, @NonNull Set<Nutzer> bettler, @NonNull Money betrag,
      @NonNull String beschreibung) {
    if(bettler.isEmpty()) {
      throw new IllegalArgumentException("Transaktionen muessen Bettler haben");
    }
    if(bettler.size() == 1 && bettler.contains(sponsor)) {
      throw new IllegalArgumentException("keine Transaktionen nur an sich selbst");
    }
    this.sponsor = sponsor;
    this.bettler = bettler;
    this.betrag = betrag;
    this.beschreibung = beschreibung;
  }

  boolean isBettler(Nutzer nutzer) {
    return bettler.contains(nutzer);
  }

  boolean isSponsor(Nutzer nutzer) {
    return nutzer.equals(sponsor);
  }

  boolean isTeilnehmer(Nutzer nutzer) {
    return isSponsor(nutzer) || isBettler(nutzer);
  }

  int countBettler() {
    return bettler.size();
  }
  String beschreibung(){
    return beschreibung;
  }

  @NonNull
  Nutzer sponsor() {
    return sponsor;
  }

  @NonNull
  Set<Nutzer> bettler() {
    return bettler;
  }

  @NonNull
  Money betrag() {
    return betrag;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this)
      return true;
    if (obj == null || obj.getClass() != this.getClass())
      return false;
    var that = (Transaktion) obj;
    return Objects.equals(this.sponsor, that.sponsor) &&
        Objects.equals(this.bettler, that.bettler) &&
        Objects.equals(this.betrag, that.betrag);
  }

  @Override
  public int hashCode() {
    return Objects.hash(sponsor, bettler, betrag);
  }

  @Override
  public String toString() {
    return "Transaction[" +
        "sponsor=" + sponsor + ", " +
        "beggars=" + bettler + ", " +
        "money=" + betrag + ']';
  }


}
