package de.propra.splitter.services;

import de.propra.splitter.domain.Group;
import de.propra.splitter.domain.User;
import java.util.HashMap;
import org.javamoney.moneta.Money;

public interface TransactionService {

  HashMap<User, HashMap<User, Money>> calculateTransactions (Group group);

}
