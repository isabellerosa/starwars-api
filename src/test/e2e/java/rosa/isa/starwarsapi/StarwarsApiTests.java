package e2e.java.rosa.isa.starwarsapi;

import com.github.javafaker.Faker;
import com.google.common.reflect.TypeToken;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import rosa.isa.starwarsapi.models.Planet;
import rosa.isa.starwarsapi.models.PlanetRegistration;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Properties;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class StarwarsApiTests {

    @BeforeAll
    public static void setup() throws IOException {
        Properties properties = getProperties();

        RestAssured.baseURI = properties.getProperty("endpoint.base-uri");
        RestAssured.basePath = properties.getProperty("endpoint.base-path");
        RestAssured.port = Integer.parseInt(properties.getProperty("endpoint.port"));
    }

    private static Properties getProperties() throws IOException {
        var propertiesFile = new File("src/test/resources/e2e.properties");

        var properties = new Properties();
        properties.load(new FileReader(propertiesFile));

        return properties;
    }

    @Test
    void addPlanet_whenCorrectPayload_thenCreated() {
        var faker = new Faker();

        var planet = new PlanetRegistration();
        planet.setName(faker.space().planet());
        planet.setClimate(faker.weather().description());
        planet.setTerrain(faker.random().hex());

        given().contentType(ContentType.JSON).body(planet)
                .when().post("/v1/planets")
                .then().statusCode(HttpStatus.SC_CREATED);

        deleteCreated(planet);
    }

    private void deleteCreated(PlanetRegistration planet) {
        var planetId = getPlanetByName(planet.getName()).getId();

        given().pathParam("id", planetId)
                .when().delete("/v1/planets/{id}");
    }

    @Test
    void addPlanet_whenCorrectlyFilled_thenConflict() {
        var originalPlanet = savePlanet();

        var planet = new PlanetRegistration();
        planet.setName(originalPlanet.getName());
        planet.setClimate(originalPlanet.getClimate());
        planet.setTerrain(originalPlanet.getTerrain());

        given().contentType(ContentType.JSON).body(planet)
                .when().post("/v1/planets")
                .then().statusCode(HttpStatus.SC_CONFLICT);

        deleteCreated(planet);
    }

    @Test
    void addPlanet_whenMissingRequiredFields_thenBadRequest() {
        var planet = new PlanetRegistration();

        given().contentType(ContentType.JSON).body(planet)
                .when().post("/v1/planets")
                .then().statusCode(HttpStatus.SC_BAD_REQUEST);
    }

    @Test
    void getPlanet_whenPlanetExists_thenOk() {
        var planet = getPlanetByName("Alderaan");

        var response = given().pathParam("id", planet.getId())
                .when().get("/v1/planets/{id}");

        assertEquals(HttpStatus.SC_OK, response.statusCode());
        assertEquals(planet, response.as(Planet.class));
    }

    private Planet getPlanetByName(String planetName) {
        List<Planet> planets = given().queryParam("name", planetName)
                .when().get("/v1/planets")
                .as(getPlanetListType());

        return planets.stream().filter(
                (planet) -> planet.getName().contentEquals(planetName))
                .findFirst().get();
    }

    private Type getPlanetListType() {
        return new TypeToken<List<Planet>>() {
        }.getType();
    }

    @Test
    void getPlanet_whenPlanetNotFound_thenNotFound() {
        var faker = new Faker();
        var planetId = faker.random().hex();

        given().pathParam("id", planetId)
                .when().get("/v1/planets/{id}")
                .then().statusCode(HttpStatus.SC_NOT_FOUND);
    }

    @Test
    void getPlanets_whenFilteringBySize_thenReturnNPlanets() {
        var size = 3;

        var response = given().queryParam("size", size)
                .when().get("/v1/planets");

        List<Planet> planets = response.as(getPlanetListType());

        assertEquals(HttpStatus.SC_OK, response.statusCode());
        assertTrue(planets.size() <= size);
    }

    @Test
    void deletePlanet_whenPlanetExists_thenOk() {
        var planet = savePlanet();

        given().pathParam("id", planet.getId())
                .when().delete("/v1/planets/{id}")
                .then().statusCode(HttpStatus.SC_NO_CONTENT);
    }

    @Test
    void deletePlanet_whenPlanetNotFound_thenNotFound() {
        var faker = new Faker();
        var planetId = faker.random().hex();

        given().pathParam("id", planetId)
                .when().delete("/v1/planets/{id}")
                .then().statusCode(HttpStatus.SC_NOT_FOUND);
    }

    private Planet savePlanet() {
        var faker = new Faker();

        var planet = new Planet();
        planet.setName(faker.space().planet());
        planet.setClimate(faker.weather().description());
        planet.setTerrain(faker.random().hex());

        given().contentType(ContentType.JSON).body(planet).post("/v1/planets");

        return getPlanetByName(planet.getName());
    }
}
