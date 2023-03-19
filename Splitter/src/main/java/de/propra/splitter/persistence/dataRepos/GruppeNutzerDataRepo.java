package de.propra.splitter.persistence.dataRepos;

import de.propra.splitter.persistence.data.GruppeNutzerData;
import java.util.HashSet;
import org.springframework.data.repository.CrudRepository;

public interface GruppeNutzerDataRepo extends CrudRepository<GruppeNutzerData, Integer> {

  HashSet<GruppeNutzerData> findAllByGruppenintid(Integer id);

  HashSet<GruppeNutzerData> findAllByNutzername(String nutzername);
}
