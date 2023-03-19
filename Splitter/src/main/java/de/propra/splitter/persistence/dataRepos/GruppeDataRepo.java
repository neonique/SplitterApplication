package de.propra.splitter.persistence.dataRepos;

import de.propra.splitter.persistence.data.GruppeData;
import java.util.HashSet;
import org.springframework.data.repository.CrudRepository;

public interface GruppeDataRepo extends CrudRepository<GruppeData, Integer> {


  GruppeData findByGruppenid(String gruppenid);

  GruppeData findByGruppenintid(Integer gruppenIntId);
}
