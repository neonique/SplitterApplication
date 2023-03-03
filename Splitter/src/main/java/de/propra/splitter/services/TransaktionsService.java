package de.propra.splitter.services;

import de.propra.splitter.domaene.Gruppe;
import de.propra.splitter.domaene.Nutzer;
import java.util.HashMap;
import org.javamoney.moneta.Money;

public interface TransaktionsService {

  HashMap<Nutzer, HashMap<Nutzer, Money>> berechneNotwendigeTransaktionen(Gruppe gruppe);
  Money berechneNutzerSaldo(Nutzer nutzer, Gruppe gruppe);
}

