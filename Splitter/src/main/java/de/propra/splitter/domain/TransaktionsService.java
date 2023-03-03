package de.propra.splitter.domain;

import de.propra.splitter.domain.Gruppe;
import de.propra.splitter.domain.Nutzer;
import java.util.HashMap;
import org.javamoney.moneta.Money;

public interface TransaktionsService {

  HashMap<Nutzer, HashMap<Nutzer, Money>> berechneNotwendigeTransaktionen(Gruppe gruppe);
  Money berechneNutzerSaldo(Nutzer nutzer, Gruppe gruppe);
}

