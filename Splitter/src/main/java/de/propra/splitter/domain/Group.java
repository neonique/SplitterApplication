package de.propra.splitter.domain;

import java.util.HashSet;
import java.util.Set;

public class Group {
  private boolean closed=false;

  private HashSet<User> participants = new HashSet<>();
  private HashSet<Transaction> transactions =new HashSet<>();

  public Group(User user) {
    participants.add(user);
  }


  public void addUser(User participant) {
    if(!transactions.isEmpty()){
      throw new RuntimeException("Users can't be added to group after first transaction");
    }
    if(this.isClosed()){
      throw new RuntimeException("Users cannot be added to closed groups");
    }
    participants.add(participant);
  }

  public Set<User> getUsers() {
    return participants;
  }

  public void addTransaction(Transaction transaction) {
    if(!participants.containsAll(transaction.Participants())){
      throw new IllegalArgumentException("Invalid user contained in transaction");
    }
    if(transaction.money().isNegativeOrZero()){
      throw new IllegalArgumentException("Transactions have to be positive");
    }
    if(this.isClosed()){
      throw new RuntimeException("Transactions cannot be added to closed groups");
    }

    transactions.add(transaction);
  }
  public Set<Transaction> getTransactions(){
    return transactions;
  }
  public boolean containsUser(User user) {
    return participants.contains(user);
  }

  public boolean isClosed(){
    return closed;
  }
  public void close(){
   closed=true;
  }
}


