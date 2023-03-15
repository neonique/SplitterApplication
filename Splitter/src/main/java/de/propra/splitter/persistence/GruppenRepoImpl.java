package de.propra.splitter.persistence;

import de.propra.splitter.domain.Gruppe;
import de.propra.splitter.domain.TransaktionDTO;
import de.propra.splitter.persistence.testdata.GruppeData;
import de.propra.splitter.service.GruppenRepo;
import java.util.List;
import java.util.Set;
import org.springframework.stereotype.Repository;

@Repository
public class GruppenRepoImpl implements GruppenRepo {

  private final GruppeDataRepo gruppeDataRepo;
  private final GruppeNutzerDataRepo gruppeNutzerDataRepo;

  private final TransaktionDataRepo transaktionDataRepo;

  private final TransaktionNutzerDataRepo transaktionNutzerDataRepo;

  public GruppenRepoImpl(GruppeDataRepo gruppeDataRepo, GruppeNutzerDataRepo gruppeNutzerDataRepo,
      TransaktionDataRepo transaktionDataRepo, TransaktionNutzerDataRepo transaktionNutzerDataRepo) {
    this.gruppeDataRepo = gruppeDataRepo;
    this.gruppeNutzerDataRepo = gruppeNutzerDataRepo;
    this.transaktionDataRepo = transaktionDataRepo;
    this.transaktionNutzerDataRepo = transaktionNutzerDataRepo;
  }

  @Override
  public void save(Gruppe gruppe) {
    GruppeData data = new GruppeData(gruppe.id(), gruppe.name(), gruppe.isclosed());
    gruppeDataRepo.save(data);
  }

  @Override
  public Gruppe load(String id) {
    GruppeData gruppeData = gruppeDataRepo.findByGruppenId(id);
    Set<String> nutzernamen = gruppeNutzerDataRepo.findGruppeNutzerDataBy_gruppeId(id);
    Gruppe gruppe = new Gruppe()
    return gruppe;
  }

  @Override
  public Set<Gruppe> nutzerGruppen(String nutzername) {
    return null;
  }

  @Override
  public Set<String> gruppeNutzer(String id) {
    return null;
  }

  @Override
  public List<TransaktionDTO> gruppeTransaktionen(String id) {
    return null;
  }

  @Override
  public boolean isClosed(String id) {
    return false;
  }

  @Override
  public String getName(String id) {
    return null;
  }

  @Override
  public boolean exists(String id) {
    return false;
  }
}
