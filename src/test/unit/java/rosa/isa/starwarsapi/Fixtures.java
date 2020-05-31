package unit.java.rosa.isa.starwarsapi;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import rosa.isa.starwarsapi.models.Planet;
import rosa.isa.starwarsapi.models.SwapiPlanet;
import rosa.isa.starwarsapi.models.SwapiResponse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Fixtures {

    public static SwapiResponse getSwapiPlanets() {
        SwapiPlanet kamino = new SwapiPlanet();
        kamino.setName("Kamino");
        kamino.setFilms(Arrays.asList("http://swapi.dev/api/films/5/"));

        SwapiPlanet kalee = new SwapiPlanet();
        kalee.setName("Kalee");
        kalee.setFilms(new ArrayList<>());

        return new SwapiResponse(2, null, null, Arrays.asList(kamino, kalee));
    }

    public static Planet getPlanet() {
        var alderaan = new Planet();
        alderaan.setName("Alderaan");
        alderaan.setClimate("temperate");
        alderaan.setId("41D3r44N");
        alderaan.setTerrain("grasslands, mountains");

        return alderaan;
    }

    public static List<Planet> getPlanets() {
        var alderaan = new Planet();
        alderaan.setId("41D3r44N");
        alderaan.setName("Alderaan");
        alderaan.setClimate("temperate");
        alderaan.setTerrain("grasslands, mountains");

        var kamino = new Planet();
        kamino.setId("K4M1N0");
        kamino.setName("Kamino");
        kamino.setClimate("temperate");
        kamino.setTerrain("ocean");

        var kashyyyk = new Planet();
        kashyyyk.setId("K45HYYYK");
        kashyyyk.setName("Kashyyyk");
        kashyyyk.setClimate("tropical");
        kashyyyk.setTerrain("jungle, forests, lakes, rivers");

        return Arrays.asList(alderaan, kamino, kashyyyk);
    }

    public static List<Planet> getPlanetsWithMatchingLetters() {
        var kamino = new Planet();
        kamino.setId("K4M1N0");
        kamino.setName("Kamino");
        kamino.setClimate("temperate");
        kamino.setTerrain("ocean");

        var kashyyyk = new Planet();
        kashyyyk.setId("K45HYYYK");
        kashyyyk.setName("Kashyyyk");
        kashyyyk.setClimate("tropical");
        kashyyyk.setTerrain("jungle, forests, lakes, rivers");

        return Arrays.asList(kamino, kashyyyk);
    }

    public static OpenAPI getOpenAPISwaggerConfig() {
        return new OpenAPI()
                .info(buildSwaggerInfo());
    }

    private static Info buildSwaggerInfo() {
        return new Info()
                .title("title")
                .description("description")
                .version("v1.0")
                .contact(buildSwaggerContactInfo());
    }

    private static Contact buildSwaggerContactInfo() {
        return new Contact()
                .name("name")
                .url("https://test.com");
    }
}
