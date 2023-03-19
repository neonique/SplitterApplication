package de.propra.splitter.service;

import de.propra.splitter.domain.Gruppe;
import de.propra.splitter.domain.TransaktionDTO;

import java.util.List;
import java.util.Set;

public interface GruppenRepo {

  public void save(Gruppe gruppe);

  public Gruppe load(String id);

  public Set<Gruppe> nutzerGruppen(String nutzername);

  public Set<String> gruppeNutzer(String id);

  public List<TransaktionDTO> gruppeTransaktionen(String id);

  public boolean isClosed(String id);

  String getName(String id);

  boolean exists(String id);

}
