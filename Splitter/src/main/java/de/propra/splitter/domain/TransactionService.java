package de.propra.splitter.domain;

import java.util.HashMap;
import org.javamoney.moneta.Money;

public interface TransactionService {

  HashMap<User, HashMap<User, Money>> calculateTransactions (Group group);

}
