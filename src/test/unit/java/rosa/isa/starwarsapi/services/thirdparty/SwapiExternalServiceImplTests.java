package unit.java.rosa.isa.starwarsapi.services.thirdparty;

import feign.RetryableException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import rosa.isa.starwarsapi.exceptions.StarWarsExternalServiceException;
import rosa.isa.starwarsapi.models.SwapiResponse;
import rosa.isa.starwarsapi.services.thirdparty.SwapiExternalServiceImpl;
import rosa.isa.starwarsapi.services.thirdparty.SwapiService;
import unit.java.rosa.isa.starwarsapi.Fixtures;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SwapiExternalServiceImplTests {
    @InjectMocks
    private SwapiExternalServiceImpl swapiExternalServiceImpl;
    @Mock
    private SwapiService swapiService;

    @Test
    void getApparitionsCount_whenPlanetUnregistered_thenReturnsZero() {
        SwapiResponse swapiResponse = Fixtures.getSwapiResponse();

        when(swapiService.getPlanetsByName(anyString())).thenReturn(swapiResponse);

        int apparitions = swapiExternalServiceImpl.getFilmApparitionsCount("Ka");

        assertEquals(0, apparitions);

        verify(swapiService, times(1)).getPlanetsByName("Ka");
    }

    @Test
    void getApparitionsCount_whenPlanetRegistered_thenReturnsApparitionsCount() {
        SwapiResponse swapiResponse = Fixtures.getSwapiResponse();

        when(swapiService.getPlanetsByName(anyString())).thenReturn(swapiResponse);

        int apparitions = swapiExternalServiceImpl.getFilmApparitionsCount("Kamino");

        assertEquals(1, apparitions);

        verify(swapiService, times(1)).getPlanetsByName("Kamino");
    }

    @Test
    void getApparitionsCount_whenAnyExceptionOccurs_thenThrowsStarWarsExternalServiceException() {
        when(swapiService.getPlanetsByName(anyString())).thenThrow(RetryableException.class);

        assertThrows(StarWarsExternalServiceException.class, () -> swapiExternalServiceImpl.getFilmApparitionsCount("Kamino"));
    }

}
