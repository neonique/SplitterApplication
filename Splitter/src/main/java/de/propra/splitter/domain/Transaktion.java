package de.propra.splitter.domain;

import java.util.Objects;
import java.util.Set;
import org.javamoney.moneta.Money;
import org.springframework.lang.NonNull;

public final class Transaktion {

  @NonNull
  private final Nutzer sponsor;
  @NonNull
  private final Set<Nutzer> bettler;
  @NonNull
  private final Money betrag;
  @NonNull
  private final String grund;

  private final Integer id;

  public Transaktion(@NonNull Nutzer sponsor, @NonNull Set<Nutzer> bettler, @NonNull Money betrag,
      @NonNull String grund) {
    this.sponsor = sponsor;
    this.bettler = bettler;
    this.betrag = betrag;
    this.grund = grund;
    this.id = null;
  }

  public Transaktion(Integer id, @NonNull Nutzer sponsor, @NonNull Set<Nutzer> bettler,
      @NonNull Money betrag,
      @NonNull String grund) {
    this.sponsor = sponsor;
    this.bettler = bettler;
    this.betrag = betrag;
    this.grund = grund;
    this.id = id;
  }

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

  @NonNull
  public Nutzer sponsor() {
    return sponsor;
  }

  public Integer id() {
    return id;
  }

  @NonNull
  public Set<Nutzer> bettler() {
    return bettler;
  }

  @NonNull
  public Money betrag() {
    return betrag;
  }

  @NonNull
  public String grund() {
    return grund;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (obj == null || obj.getClass() != this.getClass()) {
      return false;
    }
    var that = (Transaktion) obj;
    return Objects.equals(this.id, that.id) &&
        Objects.equals(this.sponsor, that.sponsor) &&
        Objects.equals(this.bettler, that.bettler) &&
        Objects.equals(this.betrag, that.betrag) &&
        Objects.equals(this.grund, that.grund);
  }

  @Override
  public int hashCode() {
    return Objects.hash(sponsor, bettler, betrag, grund);
  }

  @Override
  public String toString() {
    return "Transaktion[" +
        "sponsor=" + sponsor + ", " +
        "bettler=" + bettler + ", " +
        "betrag=" + betrag + ", " +
        "grund=" + grund + ']';
  }


}
