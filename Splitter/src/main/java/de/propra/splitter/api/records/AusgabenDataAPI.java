package de.propra.splitter.api.records;

import java.util.LinkedList;

public record AusgabenDataAPI(String grund, String glaeubiger, int cent,
                              LinkedList<String> schuldner) {

}
