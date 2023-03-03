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

    Money u1Saldo = transactionService.calculateUserBalance(user1, group);
    Money u2Saldo = transactionService.calculateUserBalance(user2, group);

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

    Money u1Saldo = transactionService.calculateUserBalance(user1, group);
    Money u2Saldo = transactionService.calculateUserBalance(user2, group);

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

    assertThat(balance.get(user1)).isNull();
    Entry<User, Money> entry = Map.entry(user1, Money.of(-15, "EUR"));
    assertThat(balance.get(user2)).containsExactly(entry);
  }
  @DisplayName("jens test 2")
  @Test
  void test_04(){
    User user1 = new User("a");
    User user2 = new User("b");
    Group group = new Group(user1);
    group.addUser(user2);
    Money m1 = Money.of(10, "EUR");
    Money m2 = Money.of(20, "EUR");
    Transaction transaction1 = new Transaction(user1, Set.of(user2, user1), m1, "");
    Transaction transaction2 = new Transaction(user2, Set.of(user2, user1), m2, "");
    group.addTransaction(transaction1);
    group.addTransaction(transaction2);

    HashMap<User, HashMap<User, Money>> balance =  transactionService.calculateNecessaryTransactions(group);

    assertThat(balance.get(user2)).isNull(); //Empty-> Null
    Entry<User, Money> entry = Map.entry(user2, Money.of(-5, "EUR"));
    assertThat(balance.get(user1)).containsExactly(entry);
  }

  @DisplayName("jens test 3")
  @Test
  void test_05(){
    User user1 = new User("a");
    User user2 = new User("b");
    Group group = new Group(user1);
    group.addUser(user2);
    Money m1 = Money.of(10, "EUR");
    Money m2 = Money.of(20, "EUR");
    Transaction transaction1 = new Transaction(user1, Set.of(user2), m1, "");
    Transaction transaction2 = new Transaction(user1, Set.of(user2, user1), m2, "");
    group.addTransaction(transaction1);
    group.addTransaction(transaction2);

    HashMap<User, HashMap<User, Money>> balance =  transactionService.calculateNecessaryTransactions(group);

    assertThat(balance.get(user1)).isNull(); //Empty-> Null
    Entry<User, Money> entry = Map.entry(user1, Money.of(-20, "EUR"));
    assertThat(balance.get(user2)).containsExactly(entry);
  }

  @DisplayName("jens test 4")
  @Test
  void test_06(){
    User user1 = new User("a");
    User user2 = new User("b");
    User user3 = new User("c");
    Group group = new Group(user1);
    group.addUser(user2);
    group.addUser(user3);
    Money m1 = Money.of(10, "EUR");
    Money m2 = Money.of(10, "EUR");
    Money m3 = Money.of(10, "EUR");
    Transaction transaction1 = new Transaction(user1, Set.of(user2, user1), m1, "");
    Transaction transaction2 = new Transaction(user2, Set.of(user2, user3), m2, "");
    Transaction transaction3= new Transaction(user3, Set.of(user3, user1), m3, "");
    group.addTransaction(transaction1);
    group.addTransaction(transaction2);
    group.addTransaction(transaction3);

    HashMap<User, HashMap<User, Money>> balance =  transactionService.calculateNecessaryTransactions(group);

    assertThat(balance.get(user1)).isNull(); //Empty-> Null
    assertThat(balance.get(user2)).isNull(); //Empty-> Null
    assertThat(balance.get(user3)).isNull(); //Empty-> Null
  }

  @DisplayName("jens test 5")
  @Test
  void test_07() {
    User user1 = new User("a");
    User user2 = new User("b");
    User user3 = new User("c");
    Group group = new Group(user1);
    group.addUser(user2);
    group.addUser(user3);
    Money m1 = Money.of(60, "EUR");
    Money m2 = Money.of(30, "EUR");
    Money m3 = Money.of(100, "EUR");
    Transaction transaction1 = new Transaction(user1, Set.of(user1, user2, user3), m1, "");
    Transaction transaction2 = new Transaction(user2, Set.of(user1, user2, user3), m2, "");
    Transaction transaction3 = new Transaction(user3, Set.of(user2, user3), m3, "");
    group.addTransaction(transaction1);
    group.addTransaction(transaction2);
    group.addTransaction(transaction3);

    HashMap<User, HashMap<User, Money>> balance = transactionService.calculateNecessaryTransactions(
        group);

    assertThat(balance.get(user1)).isNull(); //Empty-> Null
    //In den Lösungen sind folgende Entries getauscht, so ist es aber eigentlich richtig
    Entry<User, Money> entry1 = Map.entry(user1, Money.of(-30, "EUR"));
    Entry<User, Money> entry2 = Map.entry(user3, Money.of(-20, "EUR"));
    assertThat(balance.get(user2)).containsExactly(entry1,entry2);
    assertThat(balance.get(user3)).isNull(); //Empty-> Null
  }

  @DisplayName("jens test 6")
  @Test
  void test_08() {
    User user1 = new User("a");
    User user2 = new User("b");
    User user3 = new User("c");
    User user4 = new User("d");
    User user5 = new User("e");
    User user6 = new User("f");
    Group group = new Group(user1);
    group.addUser(user2);
    group.addUser(user3);
    group.addUser(user4);
    group.addUser(user5);
    group.addUser(user6);
    Money m1 = Money.of(564, "EUR");
    Money m2 = Money.of(38.58, "EUR");
    Money m3 = Money.of(38.58, "EUR");
    Money m4 = Money.of(82.11, "EUR");
    Money m5 = Money.of(96, "EUR");
    Money m6 = Money.of(95.37, "EUR");
    Transaction transaction1 = new Transaction(user1, Set.of(user1, user2, user3, user4, user5, user6), m1, "Hotelzimmer");
    Transaction transaction2 = new Transaction(user2, Set.of(user2, user1), m2, "Benzin (Hinweg)");
    Transaction transaction3 = new Transaction(user2, Set.of(user2, user1, user4), m3, "Benzin (Rückweg)");
    Transaction transaction4 = new Transaction(user3, Set.of(user3, user5, user6), m4, "Benzin");
    Transaction transaction5 = new Transaction(user4, Set.of(user1, user2, user3, user4, user5, user6), m5, "Städtetour");
    Transaction transaction6 = new Transaction(user6, Set.of(user2, user5, user6), m6, "Theatervorstellung");
    group.addTransaction(transaction1);
    group.addTransaction(transaction2);
    group.addTransaction(transaction3);
    group.addTransaction(transaction4);
    group.addTransaction(transaction5);
    group.addTransaction(transaction6);

    HashMap<User, HashMap<User, Money>> balance = transactionService.calculateNecessaryTransactions(
        group);

    assertThat(balance.get(user1)).isNull(); //Empty-> Null

    Entry<User, Money> entry1 = Map.entry(user1, Money.of(-96.78, "EUR"));
    assertThat(balance.get(user2)).containsExactly(entry1);
    Entry<User, Money> entry2 = Map.entry(user1, Money.of(-55.26, "EUR"));
    assertThat(balance.get(user3)).containsExactly(entry2);
    Entry<User, Money> entry3 = Map.entry(user1, Money.of(-26.86, "EUR"));
    assertThat(balance.get(user4)).containsExactly(entry3);
    Entry<User, Money> entry4 = Map.entry(user1, Money.of(-169.16, "EUR"));
    assertThat(balance.get(user5)).containsExactly(entry4);
    Entry<User, Money> entry5 = Map.entry(user1, Money.of(-73.79, "EUR"));
    assertThat(balance.get(user6)).containsExactly(entry5);
  }

  /*
  @DisplayName("jens test 7: minimal")
  @Test
  void test_09() {
    User user1 = new User("a");
    User user2 = new User("b");
    User user3 = new User("c");
    User user4 = new User("d");
    User user5 = new User("e");
    User user6 = new User("f");
    User user7 = new User("g");
    Group group = new Group(user1);
    group.addUser(user2);
    group.addUser(user3);
    group.addUser(user4);
    group.addUser(user5);
    group.addUser(user6);
    group.addUser(user7);
    Money m1 = Money.of(20, "EUR");
    Money m2 = Money.of(10, "EUR");
    Money m3 = Money.of(75, "EUR");
    Money m4 = Money.of(50, "EUR");
    Money m5 = Money.of(40, "EUR");
    Money m6 = Money.of(40, "EUR");
    Money m7 = Money.of(5, "EUR");
    Money m8 = Money.of(30, "EUR");
    Transaction transaction1 = new Transaction(user4, Set.of(user4,user6), m1, "");
    Transaction transaction2 = new Transaction(user7, Set.of(user2), m2, "");
    Transaction transaction3 = new Transaction(user5, Set.of(user1, user3, user5), m3, " ");
    Transaction transaction4 = new Transaction(user6, Set.of(user1, user6), m4, "");
    Transaction transaction5 = new Transaction(user5, Set.of(user4), m5, "");
    Transaction transaction6 = new Transaction(user6, Set.of(user2, user6), m6, "");
    Transaction transaction7 = new Transaction(user6, Set.of(user3), m7, "");
    Transaction transaction8 = new Transaction(user7, Set.of(user1), m8, "");

    group.addTransaction(transaction1);
    group.addTransaction(transaction2);
    group.addTransaction(transaction3);
    group.addTransaction(transaction4);
    group.addTransaction(transaction5);
    group.addTransaction(transaction6);
    group.addTransaction(transaction7);
    group.addTransaction(transaction8);

    HashMap<User, HashMap<User, Money>> balance = transactionService.calculateNecessaryTransactions(
        group);

    balance.forEach((u,b) -> System.out.println(u + "pays: " + b.toString()));

    //Assert for Minimal
    Entry<User, Money> entry1 = Map.entry(user1, Money.of(-40, "EUR"));
    Entry<User, Money> entry2 = Map.entry(user1, Money.of(-40, "EUR"));
    assertThat(balance.get(user1)).containsExactly(entry1, entry2);
    Entry<User, Money> entry3 = Map.entry(user1, Money.of(-30, "EUR"));
    assertThat(balance.get(user2)).containsExactly(entry3);
    Entry<User, Money> entry4 = Map.entry(user1, Money.of(-30, "EUR"));
    assertThat(balance.get(user3)).containsExactly(entry4);
    Entry<User, Money> entry5 = Map.entry(user1, Money.of(-30, "EUR"));
    assertThat(balance.get(user4)).containsExactly(entry5);
  }
      */
}
