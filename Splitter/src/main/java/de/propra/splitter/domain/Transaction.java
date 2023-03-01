package de.propra.splitter.domain;

import java.util.Set;
import javax.money.MonetaryAmount;
import org.javamoney.moneta.Money;
import org.springframework.lang.NonNull;

public record Transaction(@NonNull User sponsor,@NonNull Set<User> Participants, @NonNull Money money) {

}
