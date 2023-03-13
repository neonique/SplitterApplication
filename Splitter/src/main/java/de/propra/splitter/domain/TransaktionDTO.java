package de.propra.splitter.domain;

import de.propra.splitter.stereotypes.DTO;
import java.util.Set;
@DTO
public record TransaktionDTO(String sponsor, Set<String> bettler, double betrag, String grund){
}
