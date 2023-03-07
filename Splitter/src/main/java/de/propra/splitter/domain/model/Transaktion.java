package de.propra.splitter.domain.model;

import java.util.Set;
import org.javamoney.moneta.Money;
import org.springframework.lang.NonNull;

public record Transaktion(@NonNull Nutzer sponsor, @NonNull Set<Nutzer> bettler, @NonNull Money betrag, @NonNull String beschreibung) {

  public boolean isBettler(Nutzer nutzer) {
    return this.bettler.contains(nutzer);
  }

  public boolean isSponsor(Nutzer nutzer) {
    return nutzer.equals(this.sponsor);
  }

  public boolean isTeilnehmer(Nutzer nutzer) {
    return isSponsor(nutzer) || isBettler(nutzer);
  }

  public int countBettler() {
    return this.bettler.size();
  }

}
