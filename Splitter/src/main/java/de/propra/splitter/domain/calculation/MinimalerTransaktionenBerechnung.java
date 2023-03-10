package de.propra.splitter.domain.calculation;

import de.propra.splitter.domain.Gruppe;
import de.propra.splitter.domain.Nutzer;
import java.util.HashMap;

import org.javamoney.moneta.Money;

public class MinimalerTransaktionenBerechnung implements TransaktionenBerechnung {

  @Override
  public HashMap<String, HashMap<String, String>> berechneNotwendigeTransaktionen(Gruppe gruppe) {
    return null;
  }

  @Override
  public Money berechneNutzerSaldo(Nutzer nutzer, Gruppe gruppe) {
    return null;
  }
}
