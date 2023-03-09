package de.propra.splitter.domain.service;

import de.propra.splitter.domain.model.Gruppe;

import java.util.*;

import de.propra.splitter.domain.model.TransaktionDTO;
import org.springframework.stereotype.Service;

@Service
public class GruppenService {


    public HashMap<String, HashMap<String, String>> berechneNotwendigeTransaktionen
            (HashSet<String> nutzer, HashSet<TransaktionDTO> transaktionDTOs) {

        Gruppe gruppe = new Gruppe(nutzer);

        for (TransaktionDTO transaktionDTO : transaktionDTOs) {
            //je nach dem wie der betrag geschrieben ist muss mehr gemacht werden!
            gruppe.addTransaktion(transaktionDTO.sponsor(), transaktionDTO.bettler(), transaktionDTO.betrag());
        }

        return gruppe.notwendigeTransaktionen();
    }


}
