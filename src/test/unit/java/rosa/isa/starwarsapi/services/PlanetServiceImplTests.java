package rosa.isa.starwarsapi.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import rosa.isa.starwarsapi.Fixtures;
import rosa.isa.starwarsapi.exceptions.PlanetAlreadyExistsException;
import rosa.isa.starwarsapi.exceptions.PlanetNotFoundException;
import rosa.isa.starwarsapi.models.Planet;
import rosa.isa.starwarsapi.repositories.PlanetRepository;
import rosa.isa.starwarsapi.services.thirdparty.StarWarsExternalService;

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
    public void save_returnsSavedPlanet_whenNoPlanetWithName() {
        var registeredPlanet = Fixtures.getPlanet();

        when(planetRepository.findByName(anyString())).thenReturn(null);
        when(planetRepository.save(any(Planet.class))).thenReturn(registeredPlanet);

        var alderaan = new Planet();
        alderaan.setName("Alderaan");
        alderaan.setClimate("temperate");
        alderaan.setTerrain("grasslands, mountains");

        var savedPlanet = planetService.save(alderaan);

        assertEquals(alderaan.getName(), savedPlanet.getName());
        assertEquals(alderaan.getClimate(), savedPlanet.getClimate());
        assertEquals(alderaan.getTerrain(), savedPlanet.getTerrain());
        assertNotNull(savedPlanet.getId());

        verify(planetRepository, times(1)).findByName("Alderaan");
        verify(planetRepository, times(1)).save(any(Planet.class));
    }

    @Test
    public void save_throwsPlanetAlreadyExists_whenExistsPlanetWithName() {
        var registeredPlanet = Fixtures.getPlanet();

        when(planetRepository.findByName(anyString())).thenReturn(registeredPlanet);

        var newPlanet = new Planet();
        newPlanet.setName("Alderaan");
        newPlanet.setClimate("temperate");
        newPlanet.setTerrain("grasslands, mountains");

        assertThrows(PlanetAlreadyExistsException.class, () -> planetService.save(newPlanet));

        verify(planetRepository, times(1)).findByName("Alderaan");
        verify(planetRepository, never()).save(any(Planet.class));
    }

    @Test
    public void findById_returnsPlanet_WhenExistsPlanetWithId() {
        var registeredPlanet = Fixtures.getPlanet();

        when(planetRepository.findById(anyString())).thenReturn(registeredPlanet);
        when(starWarsServiceAgent.getFilmApparitionsCount(anyString())).thenReturn(2);
        var planet = planetService.findById("41D3r44N");

        assertEquals("41D3r44N", planet.getId());
        assertEquals("Alderaan", planet.getName());
        assertEquals("temperate", planet.getClimate());
        assertEquals("grasslands, mountains", planet.getTerrain());

        verify(planetRepository, times(1)).findById("41D3r44N");
        verify(starWarsServiceAgent, times(1)).getFilmApparitionsCount("Alderaan");
    }

    @Test
    public void findById_throwsPlanetNotFound_WhenNoPlanetFoundWithId() {
        when(planetRepository.findById(anyString())).thenReturn(null);

        assertThrows(PlanetNotFoundException.class, () -> planetService.findById("41D3r44N"));

        verify(planetRepository, times(1)).findById("41D3r44N");
        verify(starWarsServiceAgent, never()).getFilmApparitionsCount("Alderaan");
    }

    @Test
    public void findAll_returnsPlanets_whenNoNameProvided() {
        var registeredPlanets = Fixtures.getPlanets();

        Page<Planet> planetsPage = new PageImpl<>(registeredPlanets);

        when(planetRepository.findAll(any(Pageable.class))).thenReturn(planetsPage);
        when(starWarsServiceAgent.getFilmApparitionsCount("Alderaan")).thenReturn(2);
        when(starWarsServiceAgent.getFilmApparitionsCount("Kamino")).thenReturn(1);
        when(starWarsServiceAgent.getFilmApparitionsCount("Kashyyyk")).thenReturn(2);

        var planets = planetService.findAll(0, 5, null);

        var alderaan = planets.get(0);
        var kamino = planets.get(1);
        var kashyyyk = planets.get(2);

        assertEquals("41D3r44N", alderaan.getId());
        assertEquals("Alderaan", alderaan.getName());
        assertEquals("temperate", alderaan.getClimate());
        assertEquals("grasslands, mountains", alderaan.getTerrain());
        assertEquals(2, alderaan.getFilmApparitions());

        assertEquals("K4M1N0", kamino.getId());
        assertEquals("Kamino", kamino.getName());
        assertEquals("temperate", kamino.getClimate());
        assertEquals("ocean", kamino.getTerrain());
        assertEquals(1, kamino.getFilmApparitions());

        assertEquals("K45HYYYK", kashyyyk.getId());
        assertEquals("Kashyyyk", kashyyyk.getName());
        assertEquals("tropical", kashyyyk.getClimate());
        assertEquals("jungle, forests, lakes, rivers", kashyyyk.getTerrain());
        assertEquals(2, kashyyyk.getFilmApparitions());

        assertEquals(3, planets.size());

        verify(planetRepository, times(1)).findAll(any(Pageable.class));
        verify(starWarsServiceAgent, times(3)).getFilmApparitionsCount(anyString());
    }

    @Test
    public void findAll_returnsPlanets_whenNameProvided() {
        var registeredPlanets = Fixtures.getPlanetsWithMatchingLetters();
        Page<Planet> planetsPage = new PageImpl<>(registeredPlanets);

        when(planetRepository.findAllByNameContainingIgnoreCase(any(Pageable.class), anyString())).thenReturn(planetsPage);
        when(starWarsServiceAgent.getFilmApparitionsCount("Kamino")).thenReturn(1);
        when(starWarsServiceAgent.getFilmApparitionsCount("Kashyyyk")).thenReturn(2);

        var planets = planetService.findAll(0, 5, "ka");

        var kamiko = planets.get(0);
        var kashyyyk = planets.get(1);

        assertEquals("K4M1N0", kamiko.getId());
        assertEquals("Kamino", kamiko.getName());
        assertEquals("temperate", kamiko.getClimate());
        assertEquals("ocean", kamiko.getTerrain());
        assertEquals(1, kamiko.getFilmApparitions());

        assertEquals("K45HYYYK", kashyyyk.getId());
        assertEquals("Kashyyyk", kashyyyk.getName());
        assertEquals("tropical", kashyyyk.getClimate());
        assertEquals("jungle, forests, lakes, rivers", kashyyyk.getTerrain());
        assertEquals(2, kashyyyk.getFilmApparitions());

        assertEquals(2, planets.size());

        verify(planetRepository, times(1)).findAllByNameContainingIgnoreCase(any(Pageable.class), anyString());
        verify(starWarsServiceAgent, times(2)).getFilmApparitionsCount(anyString());
    }

    @Test
    public void deleteById_returnsDeletedPlanet_whenFindsById() {
        var registeredPlanet = Fixtures.getPlanet();

        when(planetRepository.findById(anyString())).thenReturn(registeredPlanet);
        when(planetRepository.deleteById(anyString())).thenReturn(registeredPlanet);

        var deletedPlanet = planetService.deleteById("41D3r44N");

        assertEquals("41D3r44N", deletedPlanet.getId());
        assertEquals("Alderaan", deletedPlanet.getName());
        assertEquals("temperate", deletedPlanet.getClimate());
        assertEquals("grasslands, mountains", deletedPlanet.getTerrain());

        verify(planetRepository, times(1)).findById("41D3r44N");
        verify(planetRepository, times(1)).deleteById("41D3r44N");
    }

    @Test
    public void deleteById_throwsPlanetNotFound_whenNoPlanetFoundWithId() {
        when(planetRepository.findById(anyString())).thenReturn(null);

        assertThrows(PlanetNotFoundException.class, () -> planetService.deleteById("41D3r44N"));

        verify(planetRepository, times(1)).findById("41D3r44N");
        verify(planetRepository, never()).delete(any(Planet.class));
    }
}
