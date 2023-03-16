package de.propra.splitter.persistence;

import de.propra.splitter.domain.Gruppe;
import de.propra.splitter.domain.TransaktionDTO;
import de.propra.splitter.persistence.data.GruppeData;
import de.propra.splitter.persistence.dataRepos.GruppeDataRepo;
import de.propra.splitter.persistence.dataRepos.GruppeNutzerDataRepo;
import de.propra.splitter.service.GruppenRepo;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import org.springframework.stereotype.Repository;

@Repository
public class GruppenRepoImpl implements GruppenRepo {

  private final GruppeDataRepo gruppeDataRepo;
  private final GruppeNutzerDataRepo gruppeNutzerDataRepo;

  private final TransaktionsRepoImpl transaktionsRepoImpl;

  public GruppenRepoImpl(GruppeDataRepo gruppeDataRepo, GruppeNutzerDataRepo gruppeNutzerDataRepo,
      TransaktionsRepoImpl transaktionsRepoImpl) {
    this.gruppeDataRepo = gruppeDataRepo;
    this.gruppeNutzerDataRepo = gruppeNutzerDataRepo;
    this.transaktionsRepoImpl = transaktionsRepoImpl;
  }

  @Override
  public void save(Gruppe gruppe) {
    GruppeData data = new GruppeData(gruppe.id(), gruppe.name(), gruppe.isclosed());
    gruppeDataRepo.save(data);
  }

  @Override
  public Gruppe load(String id) {
    GruppeData gruppeData = gruppeDataRepo.findByGruppenId(id);
    HashSet<String> teilnehmer = gruppeNutzerDataRepo.findGruppeNutzerDataBy_gruppeId(id);
    List<TransaktionDTO> transaktionDTOS = transaktionsRepoImpl.getTransaktionen(id);
    UUID idAsUUID = UUID.fromString(gruppeData.gruppenId());
    Gruppe gruppe = new Gruppe(gruppeData.geschlossen(), idAsUUID, teilnehmer, transaktionDTOS, gruppeData.gruppenname());
    return gruppe;
  }
  @Override
  public Set<Gruppe> nutzerGruppen(String nutzername) {
    HashSet<String> gruppenIds = gruppeNutzerDataRepo.findGruppeNutzerDataBy_nutzername(nutzername);
    Set<Gruppe> gruppen = new HashSet<>();
    for (String g:gruppenIds
    ) {
      gruppen.add(load(g));
    }
    return gruppen;
  }

  @Override
  public Set<String> gruppeNutzer(String id) {
    HashSet<String> teilnehmer = gruppeNutzerDataRepo.findGruppeNutzerDataBy_gruppeId(id);

    return teilnehmer;
  }

  @Override
  public List<TransaktionDTO> gruppeTransaktionen(String id) {
    List<TransaktionDTO> transaktionDTOS = transaktionsRepoImpl.getTransaktionen(id);

    return transaktionDTOS;
  }

  @Override
  public boolean isClosed(String id) {
    GruppeData gruppeData = gruppeDataRepo.findByGruppenId(id);

    return gruppeData.geschlossen();
  }

  @Override
  public String getName(String id) {
    GruppeData gruppeData = gruppeDataRepo.findByGruppenId(id);

    return gruppeData.gruppenname();
  }

  @Override
  public boolean exists(String id) {
    GruppeData gruppeData = gruppeDataRepo.findByGruppenId(id);
    if(gruppeData!=null){
      return true;

    }
    return false;
  }
}
