package de.propra.splitter.services;

import de.propra.splitter.domaene.Gruppe;
import de.propra.splitter.domaene.Nutzer;
import java.util.HashMap;
import org.javamoney.moneta.Money;

public class MinimalerTransaktionsService implements TransaktionsService {

  @Override
  public HashMap<Nutzer, HashMap<Nutzer, Money>> berechneNotwendigeTransaktionen(Gruppe gruppe) {
    return null;
  }

  @Override
  public Money berechneNutzerSaldo(Nutzer nutzer, Gruppe gruppe) {
    return null;
  }
}
