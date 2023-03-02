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
@Override
  public Money calculateUserSaldo(User user, Group group){
    Set<Transaction> userTransactions= group.getTransactions().stream().filter(a -> a.isParticipant(user)).collect(Collectors.toSet());
    Set<Transaction> userBeggarTransactions = userTransactions.stream().filter(a -> a.isBeggar(user)).collect(Collectors.toSet());
    Set<Transaction> userSponsorTransactions = userTransactions.stream().filter(a -> a.isSponsor(user)).collect(Collectors.toSet());

    Money beggarMoney= userBeggarTransactions.stream().map(a -> a.money().divide(a.countBeggars())).reduce(Money.of(0, "EUR"), (a,b) -> a.add(b));
    Money sponsorMoney= userSponsorTransactions.stream().map(a -> a.money()).reduce(Money.of(0, "EUR"), (a,b) -> a.add(b));

    Money saldo = sponsorMoney.subtract(beggarMoney);

    return saldo;
  }



}
