package de.propra.splitter.service;

import de.propra.splitter.domain.model.Gruppe;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Repository
public class SessionRepo implements GruppenRepo {
    private Set<Gruppe> gruppen= new HashSet<>();


    @Override
    public void save(Gruppe gruppe) {
        gruppen.add(gruppe);
    }

    @Override
    public Gruppe load(String id) {
        return gruppen.stream().filter(a -> a.Id().equals(id)).findFirst().orElse(null);
    }
}
