package de.propra.splitter.services;

import de.propra.splitter.domain.Group;
import de.propra.splitter.domain.Transaction;
import de.propra.splitter.domain.User;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.javamoney.moneta.Money;

public class EasyTransactionService implements TransactionService {

  @Override
  public HashMap<User, HashMap<User, Money>> calculateTransactions(Group group) {
    return null;
  }

  private Money calculateUserSaldo(User user, Group group){
    Set<Transaction> userTransactions= group.getTransactions().stream().filter(a -> a.isParticipant(user)).collect(Collectors.toSet());
    Set<Transaction> userBeggarTransactions = userTransactions.stream().filter(a -> a.isBeggar(user)).collect(Collectors.toSet());
    Set<Transaction> userSponsorTransactions = userTransactions.stream().filter(a -> a.isSponsor(user)).collect(Collectors.toSet());

    Map<Money, Integer> beggarMoneyUnweighted= userBeggarTransactions.stream().collect(Collectors.toMap(a -> a.money(), a-> a.countBeggars()));
    Map<Money, Integer> sponsorMoneyUnweighted= userSponsorTransactions.stream().collect(Collectors.toMap(a -> a.money(), a-> a.countBeggars()));

    Money saldo = Money.of(0, "EUR");


    return saldo;
  }



}
