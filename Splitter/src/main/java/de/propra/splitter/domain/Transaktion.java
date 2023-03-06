package de.propra.splitter.domain;

import java.util.Objects;
import java.util.Set;
import org.javamoney.moneta.Money;
import org.springframework.lang.NonNull;

record Transaktion(@NonNull Nutzer sponsor, @NonNull Set<Nutzer> bettler, @NonNull Money betrag, @NonNull String beschreibung) {

  boolean isBettler(Nutzer nutzer) {
    return this.bettler.contains(nutzer);
  }

  boolean isSponsor(Nutzer nutzer) {
    return nutzer.equals(this.sponsor);
  }

  boolean isTeilnehmer(Nutzer nutzer) {
    return isSponsor(nutzer) || isBettler(nutzer);
  }

  int countBettler() {
    return this.bettler.size();
  }

}
