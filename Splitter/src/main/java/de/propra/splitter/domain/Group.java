package de.propra.splitter.domain;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Group {

  private HashSet<User> participants = new HashSet<>();
  private HashSet<Transaction> transactions =new HashSet<>();

  public Group(User user) {
    participants.add(user);
  }


  public void addUser(User participant) {
    participants.add(participant);
  }

  public Set<User> getUsers() {
    return participants;
  }

  public void addTransaction(Transaction transaction) {
    transactions.add(transaction);
  }
  public Set<Transaction> getTransactions(){
    return transactions;
  }
}

