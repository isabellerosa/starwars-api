package rosa.isa.starwarsapi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import rosa.isa.starwarsapi.exceptions.PlanetAlreadyExistsException;
import rosa.isa.starwarsapi.exceptions.PlanetNotFoundException;
import rosa.isa.starwarsapi.models.Planet;
import rosa.isa.starwarsapi.repositories.PlanetRepository;
import rosa.isa.starwarsapi.services.thirdparty.StarWarsExternalService;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class PlanetServiceImpl implements PlanetService {

    private final PlanetRepository planetRepository;
    private final StarWarsExternalService starWarsExternalService;

    @Autowired
    public PlanetServiceImpl(PlanetRepository planetRepository, StarWarsExternalService starWarsExternalService) {
        this.planetRepository = planetRepository;
        this.starWarsExternalService = starWarsExternalService;
    }

    @Override
    public Planet save(Planet planet) {
        if (Objects.nonNull(planetRepository.findByName(planet.getName())))
            throw new PlanetAlreadyExistsException(
                    String.format("Already exists a planet registered with the name %s", planet.getName())
            );

        return planetRepository.save(planet);
    }

    @Cacheable(value = "planet", key = "#id")
    @Override
    public Planet findById(String id) {
        var planet = planetRepository.findById(id);

        if (Objects.isNull(planet))
            throw new PlanetNotFoundException(String.format("No planet found for id: %s", id));

        return this.setFilmApparitions(planet);
    }

    @Override
    public List<Planet> findAll(int page, int size, String name) {
        var pageSheet = PageRequest.of(page, size, Sort.by("name"));
        var planets = Objects.isNull(name) ? findAll(pageSheet) : findAllWithName(pageSheet, name);

        return planets.parallelStream().map(this::setFilmApparitions).collect(Collectors.toList());
    }

    private List<Planet> findAll(PageRequest page) {
        return planetRepository.findAll(page).getContent();
    }

    private List<Planet> findAllWithName(PageRequest page, String name) {
        return planetRepository.findAllByNameContainingIgnoreCase(page, name).getContent();
    }

    private Planet setFilmApparitions(Planet planet) {
        var apparitions = starWarsExternalService.getFilmApparitionsCount(planet.getName());
        planet.setFilmApparitions(apparitions);

        return planet;
    }

    @CacheEvict(value = "planet", key = "#id")
    @Override
    public Planet deleteById(String id) {
        if (Objects.isNull(planetRepository.findById(id)))
            throw new PlanetNotFoundException(String.format("No planet found for id %s", id));

        return planetRepository.deleteById(id);
    }
}
