package de.propra.splitter.persistence;

import de.propra.splitter.persistence.testdata.GruppeData;
import de.propra.splitter.persistence.testdata.TransaktionData;
import org.springframework.data.repository.CrudRepository;

public interface TransaktionDataRepo extends CrudRepository<TransaktionData, Integer> {

}
