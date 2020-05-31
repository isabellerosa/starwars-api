package unit.java.rosa.isa.starwarsapi;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import rosa.isa.starwarsapi.models.Planet;
import rosa.isa.starwarsapi.models.PlanetRegistration;
import rosa.isa.starwarsapi.models.SwapiPlanet;
import rosa.isa.starwarsapi.models.SwapiResponse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Fixtures {
    public static SwapiResponse getSwapiResponse() {
        var swapiPlanets = getSwapiPlanets();

        return SwapiResponse.builder()
                .count(swapiPlanets.size())
                .results(swapiPlanets)
                .build();
    }

    public static List<SwapiPlanet> getSwapiPlanets() {
        SwapiPlanet kamino = SwapiPlanet.builder()
                .name("Kamino")
                .films(Arrays.asList("http://swapi.dev/api/films/5/"))
                .build();

        SwapiPlanet kalee = SwapiPlanet.builder()
                .name("Kalee")
                .films(new ArrayList<>())
                .build();

        return Arrays.asList(kamino, kalee);
    }

    public static Planet getPlanet() {
        var alderaan = Planet.builder()
                .id("41D3r44N")
                .name("Alderaan")
                .climate("temperate")
                .terrain("grasslands, mountains")
                .build();

        return alderaan;
    }

    public static Planet getPlanetWithFilmApparitions() {
        var alderaan = Planet.builder()
                .id("41D3r44N")
                .name("Alderaan")
                .climate("temperate")
                .terrain("grasslands, mountains")
                .filmApparitions(2)
                .build();

        return alderaan;
    }

    public static PlanetRegistration getPlanetRegistration() {
        var alderaan = PlanetRegistration.builder()
                .name("Alderaan")
                .climate("temperate")
                .terrain("grasslands, mountains")
                .build();

        return alderaan;
    }

    public static List<Planet> getPlanets() {
        var alderaan = Planet.builder()
                .id("41D3r44N")
                .name("Alderaan")
                .climate("temperate")
                .terrain("grasslands, mountains")
                .build();

        var kamino = Planet.builder()
                .id("K4M1N0")
                .name("Kamino")
                .climate("temperate")
                .terrain("ocean")
                .build();

        var kashyyyk = Planet.builder()
                .id("K45HYYYK")
                .name("Kashyyyk")
                .climate("tropical")
                .terrain("jungle, forests, lakes, rivers")
                .build();

        return Arrays.asList(alderaan, kamino, kashyyyk);
    }

    public static List<Planet> getPlanetsWithFilmApparitions() {
        var alderaan = Planet.builder()
                .id("41D3r44N")
                .name("Alderaan")
                .climate("temperate")
                .terrain("grasslands, mountains")
                .filmApparitions(2)
                .build();

        var kamino = Planet.builder()
                .id("K4M1N0")
                .name("Kamino")
                .climate("temperate")
                .terrain("ocean")
                .filmApparitions(1)
                .build();

        var kashyyyk = Planet.builder()
                .id("K45HYYYK")
                .name("Kashyyyk")
                .climate("tropical")
                .terrain("jungle, forests, lakes, rivers")
                .filmApparitions(2)
                .build();

        return Arrays.asList(alderaan, kamino, kashyyyk);
    }

    public static List<Planet> getPlanetsWithMatchingLetters() {
        var kamino = Planet.builder()
                .id("K4M1N0")
                .name("Kamino")
                .climate("temperate")
                .terrain("ocean")
                .build();

        var kashyyyk = Planet.builder()
                .id("K45HYYYK")
                .name("Kashyyyk")
                .climate("tropical")
                .terrain("jungle, forests, lakes, rivers")
                .build();

        return Arrays.asList(kamino, kashyyyk);
    }

    public static List<Planet> getPlanetsWithMatchingLettersAndFilmApparitions() {
        var kamino = Planet.builder()
                .id("K4M1N0")
                .name("Kamino")
                .climate("temperate")
                .terrain("ocean")
                .filmApparitions(1)
                .build();

        var kashyyyk = Planet.builder()
                .id("K45HYYYK")
                .name("Kashyyyk")
                .climate("tropical")
                .terrain("jungle, forests, lakes, rivers")
                .filmApparitions(2)
                .build();

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
