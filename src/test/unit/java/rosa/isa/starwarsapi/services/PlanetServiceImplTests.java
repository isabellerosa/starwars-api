package unit.java.rosa.isa.starwarsapi.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import rosa.isa.starwarsapi.exceptions.PlanetAlreadyExistsException;
import rosa.isa.starwarsapi.exceptions.PlanetNotFoundException;
import rosa.isa.starwarsapi.models.Planet;
import rosa.isa.starwarsapi.repositories.PlanetRepository;
import rosa.isa.starwarsapi.services.PlanetServiceImpl;
import rosa.isa.starwarsapi.services.thirdparty.StarWarsExternalService;
import unit.java.rosa.isa.starwarsapi.Fixtures;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PlanetServiceImplTests {
    @Mock
    PlanetRepository planetRepository;
    @Mock
    StarWarsExternalService starWarsServiceAgent;

    @InjectMocks
    PlanetServiceImpl planetService;

    @Test
    public void save_whenNoPlanetWithName_thenReturnsSavedPlanet() {
        var registeredPlanet = Fixtures.getPlanet();

        when(planetRepository.findByName(anyString())).thenReturn(null);
        when(planetRepository.save(any(Planet.class))).thenReturn(registeredPlanet);

        var expectedPlanet = Fixtures.getPlanet();
        var planet = Fixtures.getPlanet();

        var savedPlanet = planetService.save(planet);

        assertEquals(expectedPlanet, savedPlanet);
        assertNotNull(savedPlanet.getId());

        verify(planetRepository, times(1)).findByName(planet.getName());
        verify(planetRepository, times(1)).save(any(Planet.class));
    }

    @Test
    public void save_whenExistsPlanetWithName_thenThrowsPlanetAlreadyExists() {
        var registeredPlanet = Fixtures.getPlanet();

        when(planetRepository.findByName(anyString())).thenReturn(registeredPlanet);

        var planet = Fixtures.getPlanet();

        assertThrows(PlanetAlreadyExistsException.class, () -> planetService.save(planet));

        verify(planetRepository, times(1)).findByName(planet.getName());
        verify(planetRepository, never()).save(any(Planet.class));
    }

    @Test
    public void findById_whenExistsPlanetWithId_thenReturnsPlanet() {
        var registeredPlanet = Fixtures.getPlanet();

        when(planetRepository.findById(anyString())).thenReturn(registeredPlanet);
        when(starWarsServiceAgent.getFilmApparitionsCount(anyString())).thenReturn(2);

        var expectedPlanet = Fixtures.getPlanetWithFilmApparitions();

        var planet = planetService.findById("41D3r44N");

        assertEquals(expectedPlanet, planet);

        verify(planetRepository, times(1)).findById(planet.getId());
        verify(starWarsServiceAgent, times(1)).getFilmApparitionsCount(planet.getName());
    }

    @Test
    public void findById_whenNoPlanetFoundWithId_thenThrowsPlanetNotFound() {
        when(planetRepository.findById(anyString())).thenReturn(null);

        assertThrows(PlanetNotFoundException.class, () -> planetService.findById("41D3r44N"));

        verify(planetRepository, times(1)).findById("41D3r44N");
        verify(starWarsServiceAgent, never()).getFilmApparitionsCount(anyString());
    }

    @Test
    public void findAll_whenNoNameProvided_thenReturnsPlanets() {
        var expectedPlanets = Fixtures.getPlanetsWithFilmApparitions();
        var registeredPlanets = Fixtures.getPlanets();

        Page<Planet> planetsPage = new PageImpl<>(registeredPlanets);

        when(planetRepository.findAll(any(Pageable.class))).thenReturn(planetsPage);
        when(starWarsServiceAgent.getFilmApparitionsCount("Alderaan")).thenReturn(2);
        when(starWarsServiceAgent.getFilmApparitionsCount("Kamino")).thenReturn(1);
        when(starWarsServiceAgent.getFilmApparitionsCount("Kashyyyk")).thenReturn(2);

        var planets = planetService.findAll(0, 5, null);

        assertEquals(expectedPlanets, planets);

        verify(planetRepository, times(1)).findAll(any(Pageable.class));
        verify(starWarsServiceAgent, times(3)).getFilmApparitionsCount(anyString());
    }

    @Test
    public void findAll_whenNameProvided_thenReturnsPlanets() {
        var expectedPlanets = Fixtures.getPlanetsWithMatchingLettersAndFilmApparitions();
        var registeredPlanets = Fixtures.getPlanetsWithMatchingLetters();
        Page<Planet> planetsPage = new PageImpl<>(registeredPlanets);

        when(planetRepository.findAllByNameContainingIgnoreCase(any(Pageable.class), anyString())).thenReturn(planetsPage);
        when(starWarsServiceAgent.getFilmApparitionsCount("Kamino")).thenReturn(1);
        when(starWarsServiceAgent.getFilmApparitionsCount("Kashyyyk")).thenReturn(2);

        var planets = planetService.findAll(0, 5, "ka");

        assertEquals(expectedPlanets, planets);

        verify(planetRepository, times(1)).findAllByNameContainingIgnoreCase(any(Pageable.class), anyString());
        verify(starWarsServiceAgent, times(2)).getFilmApparitionsCount(anyString());
    }

    @Test
    public void deleteById_whenFindsById_thenReturnsDeletedPlanet() {
        var expectedPlanet = Fixtures.getPlanet();
        var registeredPlanet = Fixtures.getPlanet();

        when(planetRepository.findById(anyString())).thenReturn(registeredPlanet);
        when(planetRepository.deleteById(anyString())).thenReturn(registeredPlanet);

        var deletedPlanet = planetService.deleteById("41D3r44N");

        assertEquals(expectedPlanet, deletedPlanet);

        verify(planetRepository, times(1)).findById("41D3r44N");
        verify(planetRepository, times(1)).deleteById("41D3r44N");
    }

    @Test
    public void deleteById_whenNoPlanetFoundWithId_thenThrowsPlanetNotFound() {
        when(planetRepository.findById(anyString())).thenReturn(null);

        assertThrows(PlanetNotFoundException.class, () -> planetService.deleteById("41D3r44N"));

        verify(planetRepository, times(1)).findById("41D3r44N");
        verify(planetRepository, never()).delete(any(Planet.class));
    }
}
