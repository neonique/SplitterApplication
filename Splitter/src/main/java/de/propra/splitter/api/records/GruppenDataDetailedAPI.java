package de.propra.splitter.api.records;

import java.util.LinkedList;

public record GruppenDataDetailedAPI(String gruppe, String name, LinkedList<String> personen,
                                     boolean geschlossen, LinkedList<AusgabenDataAPI> ausgaben) {

}
