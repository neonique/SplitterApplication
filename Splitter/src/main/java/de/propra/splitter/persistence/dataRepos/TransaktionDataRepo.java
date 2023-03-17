package de.propra.splitter.persistence.dataRepos;

import de.propra.splitter.persistence.data.TransaktionData;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

public interface TransaktionDataRepo extends CrudRepository<TransaktionData, Integer> {

  List<TransaktionData> findAllByGruppenintid(Integer id);
}
