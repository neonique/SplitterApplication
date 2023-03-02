package de.propra.splitter.services;

import de.propra.splitter.domain.Group;
import de.propra.splitter.domain.Transaction;
import de.propra.splitter.domain.User;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import org.javamoney.moneta.Money;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class TestTransactionService {

  TransactionService transactionService = new EasyTransactionService(); //Easy Transaction Service
  @DisplayName("saldo is calculated correctly when sponsor is no beggar")
  @Test
  void test_01(){
    User user1 = new User("Josch");
    User user2 = new User("ellis");
    Group group = new Group(user1);
    group.addUser(user2);
    Money m1 = Money.of(20, "EUR");
    Transaction transaction = new Transaction(user1, Set.of(user2), m1, "");
    group.addTransaction(transaction);

    Money u1Saldo = transactionService.calculateUserSaldo(user1, group);
    Money u2Saldo = transactionService.calculateUserSaldo(user2, group);

    assertThat(u1Saldo).isEqualTo(Money.of(20, "EUR"));
    assertThat(u2Saldo).isEqualTo(Money.of(-20, "EUR"));
  }

  @DisplayName("saldo is calculated correctly when sponsor is beggar")
  @Test
  void test_02(){
    User user1 = new User("Josch");
    User user2 = new User("ellis");
    Group group = new Group(user1);
    group.addUser(user2);
    Money m1 = Money.of(20, "EUR");
    Transaction transaction = new Transaction(user1, Set.of(user2, user1), m1, "");
    group.addTransaction(transaction);

    Money u1Saldo = transactionService.calculateUserSaldo(user1, group);
    Money u2Saldo = transactionService.calculateUserSaldo(user2, group);

    assertThat(u1Saldo).isEqualTo(Money.of(10, "EUR"));
    assertThat(u2Saldo).isEqualTo(Money.of(-10, "EUR"));
  }

  @DisplayName("jens test 1")
  @Test
  void test_03(){
    User user1 = new User("a");
    User user2 = new User("b");
    Group group = new Group(user1);
    group.addUser(user2);
    Money m1 = Money.of(10, "EUR");
    Money m2 = Money.of(20, "EUR");
    Transaction transaction1 = new Transaction(user1, Set.of(user2, user1), m1, "");
    Transaction transaction2 = new Transaction(user1, Set.of(user2, user1), m2, "");
    group.addTransaction(transaction1);
    group.addTransaction(transaction2);

    HashMap<User, HashMap<User, Money>> balance =  transactionService.calculateNecessaryTransactions(group);

    assertThat(balance.get(user1)).isEmpty();
    Entry<User, Money> entry = Map.entry(user1, Money.of(-15, "EUR"));
    assertThat(balance.get(user2)).containsExactly(entry);
  }

}
