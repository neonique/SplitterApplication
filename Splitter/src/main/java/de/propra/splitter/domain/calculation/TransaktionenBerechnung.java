package de.propra.splitter.domain.calculation;

import de.propra.splitter.domain.Gruppe;
import de.propra.splitter.domain.Nutzer;
import java.util.HashMap;
import org.javamoney.moneta.Money;

public interface TransaktionenBerechnung {

  HashMap<String, HashMap<String, Double>> berechneNotwendigeTransaktionen(Gruppe gruppe);
  Money berechneNutzerSaldo(Nutzer nutzer, Gruppe gruppe);
}

