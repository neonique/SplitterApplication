package de.propra.splitter.domain;

import java.util.HashSet;
import java.util.Set;
import org.javamoney.moneta.Money;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertThrows;



public class TestDomain {
  private User user ;
  @BeforeEach
  private void resetUser(){
    user =new User("Moaz");
  }



  @Test()
  @DisplayName("create group and add participants")
  void test_01(){
    User participant = new User("Nick");

    Group group = new Group(user);
    group.addUser(participant);

    assertThat(group.getUsers()).contains(participant, user);
  }
  @Test()
  @DisplayName("add Transaction  to group")
  void test_02(){
    //arrange
    Group group = new Group(user);
    Set<User> participants =Set.of(new User("A"),new User("b"));
    participants.forEach(group::addUser);
    Money amount= Money.of(20.50 ,"EUR");
    Transaction transaction=new Transaction(user,participants,amount);
    //act
    group.addTransaction(transaction);
    //assert
    assertThat(group.getTransactions()).contains(transaction);

  }

  @Test
  @DisplayName("Participants of transactions have to be part of the group")
  void test_03(){
    //arrange
    Group group = new Group(user);
    Set<User> participants = new HashSet<> ();
    participants.addAll(Set.of(new User("A"),new User("b")));
    participants.forEach(group::addUser);
    Money amount= Money.of(20.50 ,"EUR");
    participants.add(new User("Jeremy"));
    Transaction transaction=new Transaction(user,participants,amount);
    //act
    RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
      group.addTransaction(transaction);
    });
    //assert
    assertThat("Invalid user contained in transaction").isEqualTo(thrown.getMessage());
  }

  @Test
  @DisplayName("Transactions work even though not ever group member participates")
  void test_04(){
    //arrange
    Group group = new Group(user);
    Set<User> participants = new HashSet<> ();
    User b = new User("B");
    participants.addAll(Set.of(new User("A"),b));
    participants.forEach(group::addUser);
    Money amount= Money.of(20.50 ,"EUR");
    participants.remove(b);
    Transaction transaction=new Transaction(user,participants,amount);
    //act
    group.addTransaction(transaction);
    //assert
    assertThat(group.getTransactions()).contains(transaction);
  }

  @Test()
  @DisplayName("User can't be added after first transaction")
  void test_05(){
    //arrange
    Group group = new Group(user);
    Set<User> participants =Set.of(new User("A"),new User("b"));
    participants.forEach(group::addUser);
    Money amount= Money.of(20.50 ,"EUR");
    Transaction transaction=new Transaction(user,participants,amount);

    //act
    group.addTransaction(transaction);

    RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
      group.addUser(new User("Ellis"));
    });
    //assert
    assertThat("Users can't be added to group after first transaction").isEqualTo(thrown.getMessage());

  }

}
