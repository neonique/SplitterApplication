package de.propra.splitter.service;

import de.propra.splitter.domain.model.Gruppe;

public interface GruppenRepo {

    public void save(Gruppe gruppe);

    public Gruppe load(String id);

}
