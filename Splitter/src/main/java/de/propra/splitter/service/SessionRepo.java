package de.propra.splitter.service;

import de.propra.splitter.domain.Gruppe;
import de.propra.splitter.domain.TransaktionDTO;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
public class SessionRepo implements GruppenRepo {
    private Set<Gruppe> gruppen= new HashSet<>();


    @Override
    public void save(Gruppe gruppe) {
        gruppen.add(gruppe);
    }

    @Override
    public Gruppe load(String id) {
        return gruppen.stream().filter(a -> a.id().equals(id)).findFirst().orElse(null);
    }

    @Override
    public Set<Gruppe> nutzerGruppen(String nutzername) {
        return gruppen.stream().filter(g -> g.containsNutzer(nutzername)).collect(Collectors.toSet());
    }

    @Override
    public Set<String> gruppeNutzer(String id) {
       Gruppe gruppe = gruppen.stream().filter(a -> a.id().equals(id)).findFirst().orElse(null);
       if (gruppe == null){
           return null;
       }
       return gruppe.getTeilnehmerNamen();
    }

    @Override
    public Set<TransaktionDTO> gruppeTransaktionen(String id) {
        Gruppe gruppe = gruppen.stream().filter(a -> a.id().equals(id)).findFirst().orElse(null);
        if (gruppe == null){
            return null;
        }
        return gruppe.getTransaktionenDetails();
    }

    @Override
    public boolean isClosed(String id) {
        Gruppe gruppe = gruppen.stream().filter(a -> a.id().equals(id)).findFirst().orElse(null);
        if (gruppe == null){
            throw new IllegalArgumentException("gruppe nicht gefunden");
        }
        return gruppe.isclosed();
    }
}
