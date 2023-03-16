package de.propra.splitter.persistence;

import de.propra.splitter.domain.TransaktionDTO;
import de.propra.splitter.persistence.data.TransaktionData;
import de.propra.splitter.persistence.dataRepos.TransaktionDataRepo;
import de.propra.splitter.persistence.dataRepos.TransaktionNutzerDataRepo;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.springframework.stereotype.Repository;

@Repository
public class TransaktionsRepoImpl {

  private final TransaktionDataRepo transaktionDataRepo;

  private final TransaktionNutzerDataRepo transaktionNutzerDataRepo;

  public TransaktionsRepoImpl(TransaktionDataRepo transaktionDataRepo,
      TransaktionNutzerDataRepo transaktionNutzerDataRepo) {
    this.transaktionDataRepo = transaktionDataRepo;
    this.transaktionNutzerDataRepo = transaktionNutzerDataRepo;
  }

  public List<TransaktionDTO> getTransaktionen(String id) {
    List<TransaktionData> transaktionData = transaktionDataRepo.findAllBy_gruppeId(id);
    List<TransaktionDTO> transaktionDTOS = new ArrayList<>();
    for (TransaktionData transaktion:transaktionData) {
      Set<String> bettler = transaktionNutzerDataRepo.findAllBettlerBy_transaktionid(transaktion.id());
      TransaktionDTO transaktionDTO = new TransaktionDTO(transaktion.sponsor(), bettler,
          transaktion.betrag(), transaktion.beschreibung());
      transaktionDTOS.add(transaktionDTO);
    }
    return transaktionDTOS;
  }

}
