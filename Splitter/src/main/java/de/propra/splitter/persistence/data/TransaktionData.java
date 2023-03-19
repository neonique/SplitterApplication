package de.propra.splitter.persistence.data;

import org.springframework.data.annotation.Id;

public record TransaktionData(@Id Integer transaktionid, Integer gruppenintid, Double betrag,
                              String sponsor, String beschreibung) {

}
