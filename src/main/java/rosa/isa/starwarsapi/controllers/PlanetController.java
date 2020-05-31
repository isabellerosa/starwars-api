package rosa.isa.starwarsapi.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import rosa.isa.starwarsapi.models.Planet;
import rosa.isa.starwarsapi.models.PlanetRegistration;
import rosa.isa.starwarsapi.services.PlanetService;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("v1/planets")
public class PlanetController {

    private final PlanetService planetService;

    @Autowired
    public PlanetController(PlanetService planetService) {
        this.planetService = planetService;
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public void addPlanet(@Valid @RequestBody PlanetRegistration planetRegistration) {
        log.debug("Saving planet " + planetRegistration.getName());

        var planet = new Planet();
        BeanUtils.copyProperties(planetRegistration, planet);

        planetService.save(planet);
    }

    @GetMapping("/{id}")
    public Planet getPlanet(@PathVariable("id") String planetId) {
        log.debug(String.format("Searching planet with id %s", planetId));
        return planetService.findById(planetId);
    }

    @GetMapping
    public List<Planet> getPlanets(
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "size", defaultValue = "10") int size,
            @RequestParam(name = "name", required = false) String name) {
        log.debug(String.format("Listing %d planets at page %d", size, page));
        return planetService.findAll(--page, size, name);
    }

    @DeleteMapping("/{id}")
    public void deletePlanet(@PathVariable("id") String planetId) {
        log.debug(String.format("Deleting planet with id %s", planetId));
        planetService.deleteById(planetId);
    }
}
