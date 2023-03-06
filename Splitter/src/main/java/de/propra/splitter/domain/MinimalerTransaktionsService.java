package de.propra.splitter.domain;

import java.util.HashMap;

import org.javamoney.moneta.Money;

class MinimalerTransaktionsService implements TransaktionsService {

  @Override
  public HashMap<String, HashMap<String, String>> berechneNotwendigeTransaktionen(Gruppe gruppe) {
    return null;
  }

  @Override
  public Money berechneNutzerSaldo(Nutzer nutzer, Gruppe gruppe) {
    return null;
  }
}
