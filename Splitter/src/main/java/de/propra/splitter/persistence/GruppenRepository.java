package de.propra.splitter.persistence;
import de.propra.splitter.domain.model.Gruppe;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

public interface GruppenRepository extends CrudRepository<Gruppe, Integer>{

}
