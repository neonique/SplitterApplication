package de.propra.splitter.services;

import de.propra.splitter.domain.Group;
import de.propra.splitter.domain.NutzerSaldo;
import de.propra.splitter.domain.Transaction;
import de.propra.splitter.domain.User;

import java.util.*;
import java.util.stream.Collectors;

import org.javamoney.moneta.Money;

public class EasyTransactionService implements TransactionService {

    @Override
    public HashMap<User, HashMap<User, Money>> calculateNecessaryTransactions(Group group) {
        LinkedList<NutzerSaldo> positiveBalance = new LinkedList<>();
        LinkedList<NutzerSaldo> negativeBalance = new LinkedList<>();
        Set<User> users = group.getUsers();
        //sortiert user einer Gruppe in die jeweiligen Hashmaps je nachdem ob sie Schulden oder Guthaben haben mit eben diesen
        for (User user : users) {
            Money balance = this.calculateUserBalance(user, group);
            if (balance.isGreaterThan(Money.of(0, "EUR"))) {
                positiveBalance.add(new NutzerSaldo(user, balance));
            } else if (balance.isLessThan(Money.of(0, "EUR"))) {
                negativeBalance.add(new NutzerSaldo(user, balance));
            }
        }
        //Endergebnis Hashmap
        HashMap<User, HashMap<User, Money>> necessaryTransactions = new HashMap<>();
        /* Code smells: long comments
         *  Sortiert Geld aus negative Balance zu Positive Balance
         *  und speichert dies als transactions ab
         */

        NutzerSaldo beggar = negativeBalance.poll();
        NutzerSaldo sponsor = positiveBalance.poll();

        while (!(beggar == null) || !(sponsor == null)) {
            /*Code smells: long comments
             * Indikator für 3 faelle: A,B: Users 1. |Guthaben(A)|>|Schulden(B)| -> Schulden B = 0(wird geskipppt), Guthaben A = balance
             * repeat with A und Schuldner C (innere loop)
             * 2. Fall: |Guthaben(A)|<|Schulden(B)| -> Schulden B = Balance, Guthaben A = 0 (wird geskipt)
             * repeat mit Guthaben D und B break
             * 3.Fall |Guthaben(A)| = |Schulden(B)| -> Schulden B = Guthaben A = 0 (werden geskippt)
             * repeat mit Guthaben D und schulden D
             * */
            Money balance = sponsor.saldo().add(beggar.saldo());
            HashMap<User, Money> necessaryTransaction = new HashMap<>();

            if (balance.isPositive()) { //pos > neg
                //HIER TRANSAKTION HINZUFÜGEN
                necessaryTransactions.putIfAbsent(beggar.nutzer(), necessaryTransaction);
                necessaryTransactions.get(beggar.nutzer()).put(sponsor.nutzer(), beggar.saldo());

                sponsor.setSaldo(balance);
                beggar = negativeBalance.poll();

            } else if (balance.isNegative()) { //neg > pos
                //HIER TRANSAKTION HINZUFÜGEN
                necessaryTransactions.putIfAbsent(beggar.nutzer(), necessaryTransaction);;
                necessaryTransactions.get(beggar.nutzer()).put(sponsor.nutzer(), sponsor.saldo().negate());

                beggar.setSaldo(balance);
                sponsor = positiveBalance.poll();
            } else { //neg == pos
                //HIER TRANSAKTION HINZUFÜGEN
                necessaryTransactions.putIfAbsent(beggar.nutzer(), necessaryTransaction);;
                necessaryTransactions.get(beggar.nutzer()).put(sponsor.nutzer(), beggar.saldo());

                beggar = negativeBalance.poll();
                sponsor = positiveBalance.poll();
            }
        }
        return necessaryTransactions;
    }

    @Override
    public Money calculateUserBalance(User user, Group group) {
        Set<Transaction> userTransactions = group.getTransactions()
                .stream()
                .filter(a -> a.isParticipant(user))
                .collect(Collectors.toSet());
        Set<Transaction> userBeggarTransactions = userTransactions
                .stream()
                .filter(a -> a.isBeggar(user))
                .collect(Collectors.toSet());
        Set<Transaction> userSponsorTransactions = userTransactions
                .stream()
                .filter(a -> a.isSponsor(user))
                .collect(Collectors.toSet());

        Money beggarMoney = userBeggarTransactions
                .stream().map(a -> a.money().divide(a.countBeggars()))
                .reduce(Money.of(0, "EUR"), (a, b) -> a.add(b));
        Money sponsorMoney = userSponsorTransactions
                .stream().map(a -> a.money())
                .reduce(Money.of(0, "EUR"), (a, b) -> a.add(b));

        Money saldo = sponsorMoney.subtract(beggarMoney);

        return saldo;
    }


}
