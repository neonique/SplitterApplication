package de.propra.splitter.persistence.dataRepos;

import de.propra.splitter.persistence.data.GruppeNutzerData;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import org.springframework.data.repository.CrudRepository;

public interface GruppeNutzerDataRepo extends CrudRepository<GruppeNutzerData, Integer> {

  public HashSet<String> findGruppeNutzerDataBy_gruppeId(String id);

  public HashSet<String> findGruppeNutzerDataBy_nutzername(String nutzername);

}
