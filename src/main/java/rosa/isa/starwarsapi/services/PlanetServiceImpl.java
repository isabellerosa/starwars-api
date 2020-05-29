package rosa.isa.starwarsapi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import rosa.isa.starwarsapi.exceptions.PlanetAlreadyExistsException;
import rosa.isa.starwarsapi.exceptions.PlanetNotFoundException;
import rosa.isa.starwarsapi.models.Planet;
import rosa.isa.starwarsapi.repositories.PlanetRepository;

import java.util.List;
import java.util.Objects;

@Service
public class PlanetServiceImpl implements PlanetService {

    private final PlanetRepository planetRepository;

    @Autowired
    public PlanetServiceImpl(PlanetRepository planetRepository) {
        this.planetRepository = planetRepository;
    }

    @Override
    public Planet save(Planet planet) {
        if (Objects.nonNull(planetRepository.findByName(planet.getName())))
            throw new PlanetAlreadyExistsException(
                    String.format("Already exists a planet registed with the name %s", planet.getName())
            );

        return planetRepository.save(planet);
    }

    @Override
    public Planet findById(String id) {
        var planet = planetRepository.findById(id);

        if (Objects.isNull(planet))
            throw new PlanetNotFoundException(String.format("No planet found for id: %s", id));

        return planet;
    }

    @Override
    public Planet findByName(String name) {
        return null;
    }

    @Override
    public List<Planet> findAll(int page, int size) {
        var pageSheet = PageRequest.of(page, size);
        return planetRepository.findAll(pageSheet).getContent();
    }

    @Override
    public Planet deleteById(String id) {
        if (Objects.isNull(planetRepository.findById(id)))
            throw new PlanetNotFoundException(String.format("No planet found for id %s", id));

        return planetRepository.deleteById(id);
    }
}
