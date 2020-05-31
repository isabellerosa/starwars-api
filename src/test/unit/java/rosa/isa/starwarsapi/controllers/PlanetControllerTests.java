package unit.java.rosa.isa.starwarsapi.controllers;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import rosa.isa.starwarsapi.controllers.PlanetController;
import rosa.isa.starwarsapi.models.Planet;
import rosa.isa.starwarsapi.services.PlanetService;
import unit.java.rosa.isa.starwarsapi.Fixtures;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static unit.java.rosa.isa.starwarsapi.Utils.asJsonString;

@AutoConfigureMockMvc
@ContextConfiguration(classes = {PlanetController.class})
@WebMvcTest(PlanetController.class)
public class PlanetControllerTests {

    @MockBean
    private PlanetService planetService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @SneakyThrows
    public void deletePlanet_whenValidRequest_thenPlanetIsDeleted() {
        var deletedPlanet = Fixtures.getPlanet();

        when(planetService.deleteById(anyString())).thenReturn(null);

        var planetId = deletedPlanet.getId();

        MvcResult result = mockMvc.perform(delete(String.format("/v1/planets/%s", planetId)))
                .andExpect(status().isNoContent())
                .andReturn();

        var responseBody = result.getResponse().getContentAsString();

        assertTrue(responseBody.isEmpty());
        verify(planetService, times(1)).deleteById(planetId);
    }

    @Test
    @SneakyThrows
    public void getPlanet_whenValidRequest_thenReturnsValidPlanet() {
        var planet = Fixtures.getPlanet();

        when(planetService.findById(anyString()))
                .thenReturn(planet);

        mockMvc.perform(get(String.format("/v1/planets/%s", planet.getId())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(planet.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(planet.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.terrain").value(planet.getTerrain()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.climate").value(planet.getClimate()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.film_apparitions").value(planet.getFilmApparitions()))
                .andExpect(status().isOk());

        verify(planetService, times(1)).findById(planet.getId());
    }

    @Test
    @SneakyThrows
    public void getPlanets_whenValidRequest_thenReturnPlanetsList() {
        var planets = Fixtures.getPlanets();

        when(planetService.findAll(anyInt(), anyInt(), any()))
                .thenReturn(planets);

        mockMvc.perform(get("/v1/planets?page=1&size=10"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*]", hasSize(3)))
                .andExpect(status().isOk());

        verify(planetService, times(1)).findAll(0, 10, null);
    }

    @Test
    @SneakyThrows
    public void addPlanets_whenValidRequest_thenReturnCreatePlanet() {
        var registeredPlanet = Fixtures.getPlanet();
        var planetRegistration = Fixtures.getPlanetRegistration();

        when(planetService.save(any(Planet.class))).thenReturn(registeredPlanet);

        mockMvc.perform(post("/v1/planets")
                .content(asJsonString(planetRegistration))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        var parsedPlanet = new Planet();
        BeanUtils.copyProperties(planetRegistration, parsedPlanet);

        verify(planetService, times(1)).save(parsedPlanet);
    }
}