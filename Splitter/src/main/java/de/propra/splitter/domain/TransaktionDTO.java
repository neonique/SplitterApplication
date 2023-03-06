package de.propra.splitter.domain;

import java.util.Set;

public record TransaktionDTO(String sponsor, Set<String> bettler, String betrag, String beschreibung){
}
