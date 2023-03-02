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
    IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
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

  @Test()
  @DisplayName("Transactions can't be negative")
  void test_06(){
    //arrange
    Group group = new Group(user);
    Set<User> participants =Set.of(new User("A"),new User("b"));
    participants.forEach(group::addUser);
    Money amount= Money.of(-20.50 ,"EUR");
    Transaction transaction=new Transaction(user,participants,amount);
    //act
    IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
      group.addTransaction(transaction);
    });
    //assert
    assertThat("Transactions have to be positive").isEqualTo(thrown.getMessage());

  }

  @Test()
  @DisplayName("Transactions can't be zero")
  void test_07(){
    //arrange
    Group group = new Group(user);
    Set<User> participants =Set.of(new User("A"),new User("b"));
    participants.forEach(group::addUser);
    Money amount= Money.of(0 ,"EUR");
    Transaction transaction=new Transaction(user,participants,amount);
    //act
    IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
      group.addTransaction(transaction);
    });
    //assert
    assertThat("Transactions have to be positive").isEqualTo(thrown.getMessage());

  }

  @Test
  @DisplayName("Username has to follow Github convention")
  void test_08(){

    User user5 = new User("p3t-er");

    IllegalArgumentException thrown0 = assertThrows(IllegalArgumentException.class, () -> {
      User user0 = new User("");
    });
    IllegalArgumentException thrown1 = assertThrows(IllegalArgumentException.class, () -> {
      User user1 = new User("-peter");
    });
    IllegalArgumentException thrown2 = assertThrows(IllegalArgumentException.class, () -> {
      User user2 = new User("peter-");
    });
    IllegalArgumentException thrown3 = assertThrows(IllegalArgumentException.class, () -> {
      User user3 = new User("pet--er");
    });
    IllegalArgumentException thrown4 = assertThrows(IllegalArgumentException.class, () -> {
      User user4 = new User("pet*er");
    });

    assertThat("Username is not conform with Github convention").isEqualTo(thrown0.getMessage());
    assertThat("Username is not conform with Github convention").isEqualTo(thrown1.getMessage());
    assertThat("Username is not conform with Github convention").isEqualTo(thrown2.getMessage());
    assertThat("Username is not conform with Github convention").isEqualTo(thrown3.getMessage());
    assertThat("Username is not conform with Github convention").isEqualTo(thrown4.getMessage());
    assertThat(user5.name()).isEqualTo("p3t-er");
  }

  @Test()
  @DisplayName("Groups can be closed")
  void test_09(){
    Group g = new Group(user);

    g.close();

    assertThat(g.isClosed());
  }

  @Test()
  @DisplayName("Transactions cannot be added to closed groups")
  void test_10(){
    Group group = new Group(user);
    Money amount= Money.of(20 ,"EUR");
    Set<User> participants =Set.of(new User("A"),new User("b"));
    participants.forEach(group::addUser);
    Transaction transaction = new Transaction(user, participants, amount);

    group.close();
    RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
      group.addTransaction(transaction);
    });

    assertThat("Transactions cannot be added to closed groups").isEqualTo(thrown.getMessage());



  }

  @Test()
  @DisplayName("Users cannot be added to closed groups")
  void test_11(){
    Group group = new Group(user);
    User user2 = new User("Jeremy");

    group.close();
    RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
      group.addUser(user2);
    });

    assertThat("Users cannot be added to closed groups").isEqualTo(thrown.getMessage());
  }
  @Test()
  @DisplayName("count transaction beggars correctly, sponsor is no beggar")
  void test_12(){
    Set<User> participants =Set.of(new User("A"),new User("b"));
    Transaction transaction=new Transaction(user,participants,Money.of(90,"EUR"));

   assertThat(transaction.countBeggars()).isEqualTo(2);

  }

  @Test()
  @DisplayName("count transaction beggars correctly, sponsor is beggar")
  void test_12b(){
    Set<User> participants =Set.of(new User("A"),new User("b"), user);
    Transaction transaction=new Transaction(user,participants,Money.of(90,"EUR"));

    assertThat(transaction.countBeggars()).isEqualTo(3);
  }
  @Test()
  @DisplayName("check if user is participant of the transaction")
  void test_13(){
    User user2=new User("c");
    User user3=new User("a");
    Set<User> participants =Set.of(new User("b"),user3);
    Transaction transaction=new Transaction(user,participants,Money.of(90,"EUR"));

    assertThat(transaction.isParticipant(user));
    assertThat(transaction.isParticipant(user3));
    assertThat(transaction.isParticipant(user2)).isFalse();

  }
  @Test()
  @DisplayName("check if user is sponsor of the transaction")
  void test_14(){
    User user2=new User("c");
    User user3=new User("a");
    Set<User> participants =Set.of(new User("b"),user3);
    Transaction transaction=new Transaction(user,participants,Money.of(90,"EUR"));

    assertThat(transaction.isSponsor(user));
    assertThat(transaction.isSponsor(user3)).isFalse();
    assertThat(transaction.isSponsor(user2)).isFalse();

  }
  @Test()
  @DisplayName("check if user is beggar of the transaction")
  void test_15(){
    User user2=new User("c");
    User user3=new User("a");
    Set<User> participants =Set.of(new User("b"),user3);
    Transaction transaction=new Transaction(user,participants,Money.of(90,"EUR"));

    assertThat(transaction.isBeggar(user)).isFalse();
    assertThat(transaction.isBeggar(user3));
    assertThat(transaction.isBeggar(user2)).isFalse();

  }




}
