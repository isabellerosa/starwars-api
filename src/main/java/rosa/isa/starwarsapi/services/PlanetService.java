package rosa.isa.starwarsapi.services;

import rosa.isa.starwarsapi.models.Planet;

import java.util.List;

public interface PlanetService {
    Planet save(Planet planet);

    Planet findById(String id);

    Planet findByName(String name);

    List<Planet> findAll(int page, int size);

    Planet deleteById(String id);
}
