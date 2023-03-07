package de.propra.splitter.domain.model.calculation;

import de.propra.splitter.domain.model.Gruppe;
import de.propra.splitter.domain.model.Nutzer;
import de.propra.splitter.domain.model.Transaktion;
import java.util.*;
import java.util.stream.Collectors;

import org.javamoney.moneta.Money;

public class EinfacherTransaktionenBerechnung implements TransaktionenBerechnung {

    @Override
    public HashMap<String, HashMap<String, String>> berechneNotwendigeTransaktionen(Gruppe gruppe) {
        LinkedList<NutzerSaldo> positiveBalance = new LinkedList<>();
        LinkedList<NutzerSaldo> negativeBalance = new LinkedList<>();
        Set<Nutzer> nutzers = gruppe.teilnehmer();

        for (Nutzer nutzer : nutzers) {
            Money balance = this.berechneNutzerSaldo(nutzer, gruppe);
            if (balance.isGreaterThan(Money.of(0, "EUR"))) {
                positiveBalance.add(new NutzerSaldo(nutzer, balance));
            } else if (balance.isLessThan(Money.of(0, "EUR"))) {
                negativeBalance.add(new NutzerSaldo(nutzer, balance));
            }
        }

        HashMap<String, HashMap<String, String>> necessaryTransactions = new HashMap<>();

        NutzerSaldo beggar = negativeBalance.poll();
        NutzerSaldo sponsor = positiveBalance.poll();

        while (!(beggar == null) || !(sponsor == null)) {

            Money balance = sponsor.saldo().add(beggar.saldo());
            HashMap<String, String> necessaryTransaction = new HashMap<>();

            if (balance.isPositive()) {

                necessaryTransactions.putIfAbsent(beggar.nutzer().name(), necessaryTransaction);
                necessaryTransactions.get(beggar.nutzer().name()).put(sponsor.nutzer().name(), beggar.saldo().toString());

                sponsor.setSaldo(balance);
                beggar = negativeBalance.poll();

            } else if (balance.isNegative()) {

                necessaryTransactions.putIfAbsent(beggar.nutzer().name(), necessaryTransaction);;
                necessaryTransactions.get(beggar.nutzer().name()).put(sponsor.nutzer().name(), sponsor.saldo().negate().toString());

                beggar.setSaldo(balance);
                sponsor = positiveBalance.poll();
            } else {

                necessaryTransactions.putIfAbsent(beggar.nutzer().name(), necessaryTransaction);;
                necessaryTransactions.get(beggar.nutzer().name()).put(sponsor.nutzer().name(), beggar.saldo().toString());

                beggar = negativeBalance.poll();
                sponsor = positiveBalance.poll();
            }
        }
        return necessaryTransactions;
    }

    @Override
    public Money berechneNutzerSaldo(Nutzer nutzer, Gruppe gruppe) {
        Set<Transaktion> nutzerTransaktionen = gruppe.transaktionen()
                .stream()
                .filter(a -> a.isTeilnehmer(nutzer))
                .collect(Collectors.toSet());
        Set<Transaktion> bettlerTransaktionen = nutzerTransaktionen
                .stream()
                .filter(a -> a.isBettler(nutzer))
                .collect(Collectors.toSet());
        Set<Transaktion> sponsorTransaktionen = nutzerTransaktionen
                .stream()
                .filter(a -> a.isSponsor(nutzer))
                .collect(Collectors.toSet());

        Money beggarMoney = bettlerTransaktionen
                .stream().map(a -> a.betrag().divide(a.countBettler()))
                .reduce(Money.of(0, "EUR"), (a, b) -> a.add(b));
        Money sponsorMoney = sponsorTransaktionen
                .stream().map(a -> a.betrag())
                .reduce(Money.of(0, "EUR"), (a, b) -> a.add(b));

        Money saldo = sponsorMoney.subtract(beggarMoney);

        return saldo;
    }


}
