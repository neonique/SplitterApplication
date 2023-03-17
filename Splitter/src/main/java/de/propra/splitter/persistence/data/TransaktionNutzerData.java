package de.propra.splitter.persistence.data;

import org.springframework.data.annotation.Id;

public record TransaktionNutzerData(@Id Integer transaktionid, String nutzername) {

}
