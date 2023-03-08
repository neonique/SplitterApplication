package de.propra.splitter.domain.model;

import de.propra.splitter.stereotypes.DTO;
import java.util.Set;
@DTO
public record TransaktionDTO(String sponsor, Set<String> bettler, String betrag){
}
