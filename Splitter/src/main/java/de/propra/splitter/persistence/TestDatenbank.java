package de.propra.splitter.persistence;

import de.propra.splitter.domain.model.Gruppe;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public class TestDatenbank implements GruppenRepository{

  @Override
  public <S extends Gruppe> S save(S entity) {
    return null;
  }

  @Override
  public <S extends Gruppe> Iterable<S> saveAll(Iterable<S> entities) {
    return null;
  }

  @Override
  public Optional<Gruppe> findById(Integer integer) {
    return Optional.empty();
  }

  @Override
  public boolean existsById(Integer integer) {
    return false;
  }

  @Override
  public Iterable<Gruppe> findAll() {
    return null;
  }

  @Override
  public Iterable<Gruppe> findAllById(Iterable<Integer> integers) {
    return null;
  }

  @Override
  public long count() {
    return 0;
  }

  @Override
  public void deleteById(Integer integer) {

  }

  @Override
  public void delete(Gruppe entity) {

  }

  @Override
  public void deleteAllById(Iterable<? extends Integer> integers) {

  }

  @Override
  public void deleteAll(Iterable<? extends Gruppe> entities) {

  }

  @Override
  public void deleteAll() {

  }
}
