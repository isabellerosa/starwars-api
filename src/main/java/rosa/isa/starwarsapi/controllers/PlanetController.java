package rosa.isa.starwarsapi.controllers;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import rosa.isa.starwarsapi.models.Planet;
import rosa.isa.starwarsapi.models.PlanetRegistration;
import rosa.isa.starwarsapi.services.PlanetService;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;

@Validated
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
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Planet successfully created", content = @Content),
            @ApiResponse(responseCode = "400", description = "Planet is invalid", content = @Content),
            @ApiResponse(responseCode = "409", description = "Planet is already registered", content = @Content),
            @ApiResponse(responseCode = "500", description = "An unknown error occurred", content = @Content),
    })
    public void addPlanet(@Valid @RequestBody PlanetRegistration planetRegistration) {
        log.debug("Saving planet " + planetRegistration.getName());

        var planet = new Planet();
        BeanUtils.copyProperties(planetRegistration, planet);

        planetService.save(planet);
    }

    @GetMapping("/{id}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Planet successfully created", content = @Content),
            @ApiResponse(responseCode = "404", description = "Planet is invalid", content = @Content),
            @ApiResponse(responseCode = "502", description = "An error occurred in external service", content = @Content),
            @ApiResponse(responseCode = "500", description = "An unknown error occurred", content = @Content),
    })
    public Planet getPlanet(@PathVariable("id") String planetId) {
        log.debug(String.format("Searching planet with id %s", planetId));
        return planetService.findById(planetId);
    }

    @GetMapping
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Planet successfully created", content = @Content),
            @ApiResponse(responseCode = "502", description = "An error occurred in external service", content = @Content),
            @ApiResponse(responseCode = "500", description = "An unknown error occurred", content = @Content),
    })
    @Parameters(value = {
            @Parameter(name = "size", description = "Max size is 50")
    })
    public List<Planet> getPlanets(
            @Min(1) @RequestParam(name = "page", defaultValue = "1") int page,
            @Min(1) @Max(50) @RequestParam(name = "size", defaultValue = "10") int size,
            @RequestParam(name = "name", required = false) String name) {
        log.debug(String.format("Listing %d planets at page %d", size, page));
        return planetService.findAll(--page, size, name);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Planet successfully created", content = @Content),
            @ApiResponse(responseCode = "404", description = "Planet is invalid", content = @Content),
            @ApiResponse(responseCode = "500", description = "An unknown error occurred", content = @Content),
    })
    public void deletePlanet(@PathVariable("id") String planetId) {
        log.debug(String.format("Deleting planet with id %s", planetId));
        planetService.deleteById(planetId);
    }
}
