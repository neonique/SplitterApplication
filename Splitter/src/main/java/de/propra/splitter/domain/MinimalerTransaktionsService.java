package de.propra.splitter.domain;

import de.propra.splitter.domain.Gruppe;
import de.propra.splitter.domain.Nutzer;
import java.util.HashMap;

import de.propra.splitter.domain.TransaktionsService;
import org.javamoney.moneta.Money;

class MinimalerTransaktionsService implements TransaktionsService {

  @Override
  public HashMap<Nutzer, HashMap<Nutzer, Money>> berechneNotwendigeTransaktionen(Gruppe gruppe) {
    return null;
  }

  @Override
  public Money berechneNutzerSaldo(Nutzer nutzer, Gruppe gruppe) {
    return null;
  }
}
