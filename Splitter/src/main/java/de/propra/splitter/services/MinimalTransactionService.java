package de.propra.splitter.services;

import de.propra.splitter.domain.Group;
import de.propra.splitter.domain.User;
import java.util.HashMap;
import org.javamoney.moneta.Money;

public class MinimalTransactionService implements TransactionService{

  @Override
  public HashMap<User, HashMap<User, Money>> calculateTransactions(Group group) {
    return null;
  }

  @Override
  public Money calculateUserSaldo(User user, Group group) {
    return null;
  }
}
