package de.propra.splitter.persistence;

import de.propra.splitter.persistence.testdata.GruppeData;
import de.propra.splitter.persistence.testdata.GruppeNutzerData;
import java.util.Set;
import org.springframework.data.repository.CrudRepository;

public interface GruppeNutzerDataRepo extends CrudRepository<GruppeNutzerData, Integer> {

  public Set<String> findGruppeNutzerDataBy_gruppeId(String id);

}
