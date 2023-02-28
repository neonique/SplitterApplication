package de.propra.splitter.domain;

import java.util.Set;
import javax.money.MonetaryAmount;
import org.javamoney.moneta.Money;

public record Transaction(User sponsor, Set<User> Participants, Money money) {

}
