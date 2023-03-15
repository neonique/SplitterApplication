package de.propra.splitter.persistence;
import de.propra.splitter.persistence.testdata.GruppeData;
import org.springframework.data.repository.CrudRepository;

public interface GruppeDataRepo extends CrudRepository<GruppeData, String>{

  public GruppeData findByGruppenId(String gruppenId);

}
