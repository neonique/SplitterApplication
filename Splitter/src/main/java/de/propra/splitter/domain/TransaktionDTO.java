package de.propra.splitter.domain;

import de.propra.splitter.stereotypes.DTO;
import java.util.Objects;
import java.util.Set;

@DTO
public final class TransaktionDTO {

  private final String sponsor;
  private final Set<String> bettler;
  private final double betrag;
  private final String grund;

  private final Integer id;

  public TransaktionDTO(String sponsor, Set<String> bettler, double betrag, String grund) {
    this.sponsor = sponsor;
    this.bettler = bettler;
    this.betrag = betrag;
    this.grund = grund;
    this.id = null;
  }
  public TransaktionDTO(Integer id, String sponsor, Set<String> bettler, double betrag, String grund) {
    this.sponsor = sponsor;
    this.bettler = bettler;
    this.betrag = betrag;
    this.grund = grund;
    this.id = id;
  }
  public String sponsor() {
    return sponsor;
  }

  public Integer id() {
    return id;
  }

  public Set<String> bettler() {
    return bettler;
  }

  public double betrag() {
    return betrag;
  }

  public String grund() {
    return grund;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this)
      return true;
    if (obj == null || obj.getClass() != this.getClass())
      return false;
    var that = (TransaktionDTO) obj;
    return Objects.equals(this.sponsor, that.sponsor) &&
        Objects.equals(this.bettler, that.bettler) &&
        Double.doubleToLongBits(this.betrag) == Double.doubleToLongBits(that.betrag) &&
        Objects.equals(this.grund, that.grund);
  }

  @Override
  public int hashCode() {
    return Objects.hash(sponsor, bettler, betrag, grund);
  }

  @Override
  public String toString() {
    return "TransaktionDTO[" +
        "sponsor=" + sponsor + ", " +
        "bettler=" + bettler + ", " +
        "betrag=" + betrag + ", " +
        "grund=" + grund + ']';
  }

}
