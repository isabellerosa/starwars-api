package rosa.isa.starwarsapi.services;

import rosa.isa.starwarsapi.models.Planet;

import java.util.List;

public interface PlanetService {
    Planet save(Planet planet);

    Planet findById(String id);

    List<Planet> findAll(int page, int size, String name);

    Planet deleteById(String id);
}
