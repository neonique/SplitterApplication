package de.propra.splitter.persistence.data;

import org.springframework.data.annotation.Id;

public record GruppeNutzerData(@Id Integer id, Integer gruppenIntId, String nutzername) {

}
