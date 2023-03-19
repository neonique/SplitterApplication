package de.propra.splitter.persistence;

import de.propra.splitter.domain.Gruppe;
import de.propra.splitter.domain.TransaktionDTO;
import de.propra.splitter.persistence.data.GruppeData;
import de.propra.splitter.persistence.data.GruppeNutzerData;
import de.propra.splitter.persistence.data.TransaktionData;
import de.propra.splitter.persistence.data.TransaktionNutzerData;
import de.propra.splitter.persistence.dataRepos.GruppeDataRepo;
import de.propra.splitter.persistence.dataRepos.GruppeNutzerDataRepo;
import de.propra.splitter.persistence.dataRepos.TransaktionDataRepo;
import de.propra.splitter.persistence.dataRepos.TransaktionNutzerDataRepo;
import de.propra.splitter.service.GruppenRepo;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.stereotype.Repository;

@Repository("postgresql")
public class GruppenRepoImpl implements GruppenRepo {

  GruppeDataRepo gruppeDataRepo;
  GruppeNutzerDataRepo gruppeNutzerDataRepo;
  TransaktionNutzerDataRepo transaktionNutzerDataRepo;
  TransaktionDataRepo transaktionDataRepo;

  public GruppenRepoImpl(GruppeDataRepo gruppeDataRepo, GruppeNutzerDataRepo gruppeNutzerDataRepo,
      TransaktionNutzerDataRepo transaktionNutzerDataRepo,
      TransaktionDataRepo transaktionDataRepo) {
    this.gruppeDataRepo = gruppeDataRepo;
    this.gruppeNutzerDataRepo = gruppeNutzerDataRepo;
    this.transaktionNutzerDataRepo = transaktionNutzerDataRepo;
    this.transaktionDataRepo = transaktionDataRepo;
  }

  @Override
  public void save(Gruppe gruppe) {

    GruppeData gruppeData = gruppeDataRepo.findByGruppenid(gruppe.id());
    Integer id = null;

    if(gruppeData != null) {
      id = gruppeData.gruppenintid();
    }

    gruppeData = new GruppeData(id, gruppe.id(), gruppe.name(), gruppe.isclosed());
    gruppeData = gruppeDataRepo.save(gruppeData);

    saveGruppeNutzerData(gruppe, gruppeData);

    saveTransaktionenNutzerData(gruppe, gruppeData);
  }

  private void saveGruppeNutzerData(Gruppe gruppe, GruppeData gruppeData) {
    for (String teilnehmerName : gruppe.getTeilnehmerNamen()) {
      GruppeNutzerData gruppeNutzerData = new GruppeNutzerData(null, gruppeData.gruppenintid(),
          teilnehmerName);
      gruppeNutzerDataRepo.save(gruppeNutzerData);
    }
  }

  private void saveTransaktionenNutzerData(Gruppe gruppe, GruppeData gruppeData) {
    for (TransaktionDTO transaktionenDetail : gruppe.getTransaktionenDetails()) {
      TransaktionData transaktionData = new TransaktionData(transaktionenDetail.id(),
          gruppeData.gruppenintid(), transaktionenDetail.betrag(), transaktionenDetail.sponsor(),
          transaktionenDetail.grund());
          transaktionData = transaktionDataRepo.save(transaktionData);

      for (String bettler : transaktionenDetail.bettler()) {
        TransaktionNutzerData transaktionNutzerData = new TransaktionNutzerData(null,
            transaktionData.transaktionid(), bettler);
            transaktionNutzerDataRepo.save(transaktionNutzerData);
      }
    }
  }

  @Override
  public Gruppe load(String id) {

    GruppeData gruppeData = gruppeDataRepo.findByGruppenid(id);
    HashSet<GruppeNutzerData> teilnehmer = gruppeNutzerDataRepo.findAllByGruppenintid(gruppeData.gruppenintid());
    Set<String> teilnehmerNamen = teilnehmer.stream().map(t -> t.nutzername()).collect(
        Collectors.toSet());

    List<TransaktionDTO> transaktionDTOS = this.gruppeTransaktionen(id);

    UUID idAsUUID = UUID.fromString(gruppeData.gruppenid());
    Gruppe gruppe = new Gruppe(gruppeData.geschlossen(), idAsUUID, new HashSet<>(teilnehmerNamen), transaktionDTOS, gruppeData.gruppenname());
    return gruppe;
  }

  @Override
  public Set<Gruppe> nutzerGruppen(String nutzername) {

    HashSet<GruppeNutzerData> gruppenIntIds = gruppeNutzerDataRepo.findAllByNutzername(nutzername);
    Set<String > gruppenIds = gruppenIntIds
        .stream()
        .map(i -> gruppeDataRepo.findByGruppenintid(i.gruppenintid()).gruppenid())
        .collect(Collectors.toSet());

    Set<Gruppe> gruppen = gruppenIds
        .stream()
        .map(i -> this.load(i))
        .collect(Collectors.toSet());

    return gruppen;

  }
  @Override
  public Set<String> gruppeNutzer(String id) {
    GruppeData gruppeData = gruppeDataRepo.findByGruppenid(id);
    HashSet<GruppeNutzerData> teilnehmer = gruppeNutzerDataRepo.findAllByGruppenintid(gruppeData.gruppenintid());
    Set<String> teilnehmerNamen = teilnehmer.stream().map(t -> t.nutzername()).collect(
        Collectors.toSet());
    return teilnehmerNamen;
  }

  @Override
  public List<TransaktionDTO> gruppeTransaktionen(String id) {

    GruppeData gruppeData = gruppeDataRepo.findByGruppenid(id);
    Integer intId = gruppeData.gruppenintid();

    List<TransaktionData> transaktionData = transaktionDataRepo.findAllByGruppenintid(intId);
    return getTransaktionDTOSFromData(transaktionData);
  }

  private List<TransaktionDTO> getTransaktionDTOSFromData(List<TransaktionData> transaktionData) {
    List<TransaktionDTO> transaktionDTOS = new ArrayList<>();
    for (TransaktionData transaktion: transaktionData) {
      Set<String> bettler = transaktionNutzerDataRepo
          .findAllByTransaktionid(transaktion.transaktionid())
          .stream()
          .map(t -> t.nutzername())
          .collect(Collectors.toSet());
      TransaktionDTO transaktionDTO = new TransaktionDTO(transaktion.transaktionid(),
          transaktion.sponsor(), bettler, transaktion.betrag(), transaktion.beschreibung());
      transaktionDTOS.add(transaktionDTO);
    }
    return transaktionDTOS;
  }

  @Override
  public boolean isClosed(String id) {
    return gruppeDataRepo.findByGruppenid(id).geschlossen();
  }

  @Override
  public String getName(String id) {
    return gruppeDataRepo.findByGruppenid(id).gruppenname();
  }

  @Override
  public boolean exists(String id) {
    GruppeData gruppeData = gruppeDataRepo.findByGruppenid(id);
    if(gruppeData == null){
      return false;
    }
    return true;
  }
}
