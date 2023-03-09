package de.propra.splitter.domain.service;

import de.propra.splitter.domain.model.Gruppe;

import de.propra.splitter.persistence.GruppenRepository;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.javamoney.moneta.Money;

import de.propra.splitter.domain.model.TransaktionDTO;
import org.springframework.stereotype.Service;

import javax.money.MonetaryAmount;

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
