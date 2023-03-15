package de.propra.splitter.persistence;

import de.propra.splitter.persistence.testdata.GruppeData;
import de.propra.splitter.persistence.testdata.TransaktionNutzerData;
import org.springframework.data.repository.CrudRepository;

public interface TransaktionNutzerDataRepo extends CrudRepository<TransaktionNutzerData, Integer> {

}
