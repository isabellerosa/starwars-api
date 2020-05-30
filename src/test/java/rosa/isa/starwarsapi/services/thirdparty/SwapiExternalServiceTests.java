package rosa.isa.starwarsapi.services.thirdparty;

import feign.RetryableException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import rosa.isa.starwarsapi.Fixtures;
import rosa.isa.starwarsapi.exceptions.StarWarsExternalServiceException;
import rosa.isa.starwarsapi.models.SwapiResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SwapiExternalServiceTests {
    @InjectMocks
    private SwapiExternalService swapiExternalService;
    @Mock
    private SwapiService swapiService;

    @Test
    void getApparitionsCount_returnsZero_whenPlanetUnregistered() {
        SwapiResponse swapiResponse = Fixtures.getSwapiPlanets();

        when(swapiService.getPlanetsByName(anyString())).thenReturn(swapiResponse);

        int apparitions = swapiExternalService.getFilmApparitionsCount("Ka");

        assertEquals(0, apparitions);

        verify(swapiService, times(1)).getPlanetsByName("Ka");
    }

    @Test
    void getApparitionsCount_ReturnsApparitions_whenPlanetRegistered() {
        SwapiResponse swapiResponse = Fixtures.getSwapiPlanets();

        when(swapiService.getPlanetsByName(anyString())).thenReturn(swapiResponse);

        int apparitions = swapiExternalService.getFilmApparitionsCount("Kamino");

        assertEquals(1, apparitions);

        verify(swapiService, times(1)).getPlanetsByName("Kamino");
    }

    @Test
    void getApparitionsCount_throwsStarWarsExternalServiceException_whenAnyExceptionOccurs() {
        when(swapiService.getPlanetsByName(anyString())).thenThrow(RetryableException.class);

        assertThrows(StarWarsExternalServiceException.class, () -> swapiExternalService.getFilmApparitionsCount("Kamino"));
    }

}