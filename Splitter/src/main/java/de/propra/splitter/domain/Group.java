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
    if(!transactions.isEmpty()){
      throw new RuntimeException("Users can't be added to group after first transaction");
    }
    participants.add(participant);
  }

  public Set<User> getUsers() {
    return participants;
  }

  public void addTransaction(Transaction transaction) {
    if(!participants.containsAll(transaction.Participants())){
      throw new RuntimeException("Invalid user contained in transaction");
    }
    if(transaction.money().isNegativeOrZero()){
      throw new RuntimeException("Transactions have to be positive");
    }

    transactions.add(transaction);
  }
  public Set<Transaction> getTransactions(){
    return transactions;
  }
}

