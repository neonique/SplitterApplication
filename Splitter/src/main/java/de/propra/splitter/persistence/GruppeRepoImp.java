package de.propra.splitter.persistence;

import de.propra.splitter.domain.Gruppe;
import de.propra.splitter.domain.TransaktionDTO;
import de.propra.splitter.service.GruppenRepo;
import java.util.List;
import java.util.Set;

public class GruppeRepoImp implements GruppenRepo {

  @Override
  public void save(Gruppe gruppe) {

  }

  @Override
  public Gruppe load(String id) {
    return null;
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
