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
    HashMap<User,Money>  positiveBalance=new HashMap<>();
    HashMap<User,Money>  negativeBalance=new HashMap<>();
    Set<User> users=group.getUsers();
    for (User user:users){
      Money balance =this.calculateUserBalance(user,group);
      if(balance.isGreaterThan(Money.of(0,"EUR"))){
        positiveBalance.put(user,balance);
      }
      else if(balance.isLessThan(Money.of(0,"EUR"))){
        negativeBalance.put(user,balance);
      }
    }
    HashMap<User,HashMap<User,Money>> necessaryTransactions=new HashMap<>();
    for (Map.Entry<User,Money> positiveEntry:positiveBalance.entrySet()) {

     // positiveBalance.remove(positiveEntry.getKey());
    }
    return necessaryTransactions;
  }
@Override
  public Money calculateUserBalance(User user, Group group){
    Set<Transaction> userTransactions= group.getTransactions().stream().filter(a -> a.isParticipant(user)).collect(Collectors.toSet());
    Set<Transaction> userBeggarTransactions = userTransactions.stream().filter(a -> a.isBeggar(user)).collect(Collectors.toSet());
    Set<Transaction> userSponsorTransactions = userTransactions.stream().filter(a -> a.isSponsor(user)).collect(Collectors.toSet());

    Money beggarMoney= userBeggarTransactions.stream().map(a -> a.money().divide(a.countBeggars())).reduce(Money.of(0, "EUR"), (a,b) -> a.add(b));
    Money sponsorMoney= userSponsorTransactions.stream().map(a -> a.money()).reduce(Money.of(0, "EUR"), (a,b) -> a.add(b));

    Money saldo = sponsorMoney.subtract(beggarMoney);

    return saldo;
  }



}
