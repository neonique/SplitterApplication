package de.propra.splitter.persistence.data;

import org.springframework.data.annotation.Id;

public record GruppeData(@Id Integer gruppenIntId, String gruppenid, String gruppenname, boolean geschlossen) {

}
