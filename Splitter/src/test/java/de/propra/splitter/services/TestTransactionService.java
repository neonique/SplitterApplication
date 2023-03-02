package de.propra.splitter.services;

import de.propra.splitter.domain.Group;
import de.propra.splitter.domain.Transaction;
import de.propra.splitter.domain.User;
import java.util.Set;
import org.javamoney.moneta.Money;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class TestTransactionService {

  TransactionService transactionService = new EasyTransactionService(); //Easy Transaction Service
  @DisplayName("")
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

  @DisplayName("")
  @Test
  void test_02(){

  }

}
