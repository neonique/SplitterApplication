package de.propra.splitter.domain;

import java.util.*;
import java.util.stream.Collectors;

import org.javamoney.moneta.Money;

class EinfacherTransaktionsService implements TransaktionsService {

    @Override
    public HashMap<Nutzer, HashMap<Nutzer, Money>> berechneNotwendigeTransaktionen(Gruppe gruppe) {
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

        HashMap<Nutzer, HashMap<Nutzer, Money>> necessaryTransactions = new HashMap<>();

        NutzerSaldo beggar = negativeBalance.poll();
        NutzerSaldo sponsor = positiveBalance.poll();

        while (!(beggar == null) || !(sponsor == null)) {

            Money balance = sponsor.saldo().add(beggar.saldo());
            HashMap<Nutzer, Money> necessaryTransaction = new HashMap<>();

            if (balance.isPositive()) {

                necessaryTransactions.putIfAbsent(beggar.nutzer(), necessaryTransaction);
                necessaryTransactions.get(beggar.nutzer()).put(sponsor.nutzer(), beggar.saldo());

                sponsor.setSaldo(balance);
                beggar = negativeBalance.poll();

            } else if (balance.isNegative()) {

                necessaryTransactions.putIfAbsent(beggar.nutzer(), necessaryTransaction);;
                necessaryTransactions.get(beggar.nutzer()).put(sponsor.nutzer(), sponsor.saldo().negate());

                beggar.setSaldo(balance);
                sponsor = positiveBalance.poll();
            } else {

                necessaryTransactions.putIfAbsent(beggar.nutzer(), necessaryTransaction);;
                necessaryTransactions.get(beggar.nutzer()).put(sponsor.nutzer(), beggar.saldo());

                beggar = negativeBalance.poll();
                sponsor = positiveBalance.poll();
            }
        }
        return necessaryTransactions;
    }

    @Override
    public Money berechneNutzerSaldo(Nutzer nutzer, Gruppe gruppe) {
        Set<Transaktion> userTransaktions = gruppe.transaktionen()
                .stream()
                .filter(a -> a.isTeilnehmer(nutzer))
                .collect(Collectors.toSet());
        Set<Transaktion> userBeggarTransaktions = userTransaktions
                .stream()
                .filter(a -> a.isBettler(nutzer))
                .collect(Collectors.toSet());
        Set<Transaktion> userSponsorTransaktions = userTransaktions
                .stream()
                .filter(a -> a.isSponsor(nutzer))
                .collect(Collectors.toSet());

        Money beggarMoney = userBeggarTransaktions
                .stream().map(a -> a.betrag().divide(a.countBettler()))
                .reduce(Money.of(0, "EUR"), (a, b) -> a.add(b));
        Money sponsorMoney = userSponsorTransaktions
                .stream().map(a -> a.betrag())
                .reduce(Money.of(0, "EUR"), (a, b) -> a.add(b));

        Money saldo = sponsorMoney.subtract(beggarMoney);

        return saldo;
    }


}
