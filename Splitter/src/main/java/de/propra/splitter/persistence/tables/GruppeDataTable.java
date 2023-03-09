package de.propra.splitter.persistence.tables;

import de.propra.splitter.persistence.GruppeDataRepository;
import de.propra.splitter.persistence.testdata.GruppeData;
import java.util.HashSet;
import java.util.Optional;

public class GruppeDataTable implements GruppeDataRepository {

  HashSet<GruppeData> gruppeData = new HashSet<>();

  @Override
  public <S extends GruppeData> S save(S entity) {
    GruppeData data = new GruppeData(entity.id(), entity.gruppenName(), entity.geschlossen());
    gruppeData.add(data);

    return null;
  }

  @Override
  public <S extends GruppeData> Iterable<S> saveAll(Iterable<S> entities) {
    for (GruppeData entity: entities) {
      save(entity);
    }

    return null;
  }

  @Override
  public Optional<GruppeData> findById(Integer integer) {
    return Optional.empty();
  }

  @Override
  public boolean existsById(Integer integer) {
    return false;
  }

  @Override
  public Iterable<GruppeData> findAll() {
    return null;
  }

  @Override
  public Iterable<GruppeData> findAllById(Iterable<Integer> integers) {
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
  public void delete(GruppeData entity) {

  }

  @Override
  public void deleteAllById(Iterable<? extends Integer> integers) {

  }

  @Override
  public void deleteAll(Iterable<? extends GruppeData> entities) {

  }

  @Override
  public void deleteAll() {

  }
}
