package de.propra.splitter.persistence.dataRepos;
import de.propra.splitter.persistence.data.GruppeData;
import org.springframework.data.repository.CrudRepository;

public interface GruppeDataRepo extends CrudRepository<GruppeData, String>{

  public GruppeData findByGruppenid(String gruppenId);

}
