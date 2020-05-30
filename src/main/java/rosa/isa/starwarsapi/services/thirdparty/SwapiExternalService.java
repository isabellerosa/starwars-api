package rosa.isa.starwarsapi.services.thirdparty;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rosa.isa.starwarsapi.exceptions.StarWarsExternalServiceException;
import rosa.isa.starwarsapi.models.SwapiResponse;

import java.util.NoSuchElementException;

@Slf4j
@Service
public class SwapiExternalService implements StarWarsExternalService {

    private final SwapiService swapiService;

    @Autowired
    public SwapiExternalService(SwapiService swapiService) {
        this.swapiService = swapiService;
    }

    @Override
    public Integer getFilmApparitionsCount(String planetName) {
        log.debug(String.format("Trying to find film apparitions for planet %s", planetName));

        try {
            SwapiResponse foundPlanets = swapiService.getPlanetsByName(planetName);

            var planet = foundPlanets.getResults().stream()
                    .filter((foundPlanet) -> foundPlanet.getName().equalsIgnoreCase(planetName)).findFirst().get();

            return planet.getFilms().size();
        } catch (NoSuchElementException noSuchElementException) {
            return 0;
        } catch (Exception exception) {
            var errorMessage = "An unknown error occurred";
            log.error(errorMessage, exception);

            throw new StarWarsExternalServiceException(errorMessage);
        }
    }
}
