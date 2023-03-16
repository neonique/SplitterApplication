package de.propra.splitter.persistence;

import de.propra.splitter.persistence.testdata.GruppeData;
import de.propra.splitter.persistence.testdata.TransaktionNutzerData;
import java.util.Set;
import org.springframework.data.repository.CrudRepository;

public interface TransaktionNutzerDataRepo extends CrudRepository<TransaktionNutzerData, Integer> {

  Set<String> findAllTransaktionsBettler(int id);

}
