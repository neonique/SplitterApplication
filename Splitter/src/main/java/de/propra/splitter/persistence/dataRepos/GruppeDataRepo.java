package de.propra.splitter.persistence.dataRepos;
import de.propra.splitter.persistence.data.GruppeData;
import org.springframework.data.repository.CrudRepository;

public interface GruppeDataRepo extends CrudRepository<GruppeData, Integer>{

  public GruppeData findByGruppenid(String gruppenId);
  public GruppeData findByGruppenIntId(Integer gruppenIntId);

}
