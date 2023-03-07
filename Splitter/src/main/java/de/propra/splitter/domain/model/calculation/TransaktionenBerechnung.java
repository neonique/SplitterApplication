package de.propra.splitter.domain.model.calculation;

import de.propra.splitter.domain.model.Gruppe;
import de.propra.splitter.domain.model.Nutzer;
import java.util.HashMap;
import org.javamoney.moneta.Money;

public interface TransaktionenBerechnung {

  HashMap<String, HashMap<String, String>> berechneNotwendigeTransaktionen(Gruppe gruppe);
  Money berechneNutzerSaldo(Nutzer nutzer, Gruppe gruppe);
}

