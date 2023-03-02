package de.propra.splitter.services;

import de.propra.splitter.domain.Group;
import de.propra.splitter.domain.Transaction;
import de.propra.splitter.domain.User;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.javamoney.moneta.Money;

public class EasyTransactionService implements TransactionService {

    @Override
    public HashMap<User, HashMap<User, Money>> calculateNecessaryTransactions(Group group) {
        HashMap<User, Money> positiveBalance = new HashMap<>();
        HashMap<User, Money> negativeBalance = new HashMap<>();
        Set<User> users = group.getUsers();
        //sortiert user einer Gruppe in die jeweiligen Hashmaps je nachdem ob sie Schulden oder Guthaben haben mit eben diesen
        for (User user : users) {
            Money balance = this.calculateUserBalance(user, group);
            if (balance.isGreaterThan(Money.of(0, "EUR"))) {
                positiveBalance.put(user, balance);
            } else if (balance.isLessThan(Money.of(0, "EUR"))) {
                negativeBalance.put(user, balance);
            }
        }

        //Endergebnis Hashmap
        HashMap<User, HashMap<User, Money>> necessaryTransactions = new HashMap<>();

        /* Code smells: long comments
        *  Sortiert Geld aus negative Balance zu Positive Balance
        *  und speichert dies als transactions ab
        */
        for (Map.Entry<User, Money> positiveEntry : positiveBalance.entrySet()) {
            for (Map.Entry<User, Money> negativeEntry : negativeBalance.entrySet()) {
                if (positiveEntry.getValue().isNegativeOrZero()) {
                    positiveBalance.remove(positiveEntry.getKey());
                    break;
                }
                /*Code smells: long comments
                * Indikator für 3 faelle: A,B: Users 1. |Guthaben(A)|>|Schulden(B)| -> Schulden B = 0(wird entfernt), Guthaben A = balance
                * repeat with A und Schuldner C (innere loop)
                * 2. Fall: |Guthaben(A)|<|Schulden(B)| -> Schulden B = Balance, Guthaben A = 0 (wird entfernt)
                * repeat mit Guthaben D und B break
                * 3.Fall |Guthaben(A)| = |Schulden(B)| -> Schulden B = Guthaben A = 0 (werden entfernt)
                * repeat mit Guthaben D und schulden D
                * */
                Money balance = positiveEntry.getValue().add(negativeEntry.getValue());
                Map.Entry<User, Money> singleTransaction = Map.entry(positiveEntry.getKey(), negativeEntry.getValue().add(balance));
                negativeEntry.setValue(negativeEntry.getValue().subtract(balance));

                necessaryTransactions.put(negativeEntry.getKey(), Map.of(singleTransaction));



                if(balance.isPositiveOrZero()) {
                  positiveEntry.setValue(balance);
                  negativeBalance.remove(negativeEntry.getKey());
                  //continue automatisch?
                }
              if(balance.isNegativeOrZero()) {
                negativeEntry.setValue(balance);
                positiveBalance.remove(positiveEntry.getKey());
                //continue automatisch?
              }
            }
            // positiveBalance.remove(positiveEntry.getKey());
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
