package de.propra.splitter.persistence.dataRepos;

import de.propra.splitter.persistence.data.TransaktionNutzerData;
import java.util.Set;
import org.springframework.data.repository.CrudRepository;

public interface TransaktionNutzerDataRepo extends CrudRepository<TransaktionNutzerData, Integer> {
  Set<TransaktionNutzerData> findAllByTransaktionid(Integer transaktionid);
}
