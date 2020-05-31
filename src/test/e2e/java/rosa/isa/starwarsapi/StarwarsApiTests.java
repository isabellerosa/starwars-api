package rosa.isa.starwarsapi;

import com.github.javafaker.Faker;
import com.google.common.reflect.TypeToken;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import rosa.isa.starwarsapi.models.Planet;
import rosa.isa.starwarsapi.models.PlanetRegistration;

import java.lang.reflect.Type;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class StarwarsApiTests {

    @BeforeAll
    public static void setup() {
        RestAssured.basePath = "/api/v1";
        RestAssured.port = 8080;
    }

    @Test
    void addPlanet_whenCorrectPayload_thenCreated() {
        var faker = new Faker();
        var newPlanetName = faker.space().planet();

        var planet = new PlanetRegistration();
        planet.setName(newPlanetName);

        given().contentType(ContentType.JSON).body(planet)
                .when().post("/planets")
                .then().statusCode(HttpStatus.SC_CREATED);
    }

    @Test
    void addPlanet_whenCorrectlyFilled_thenConflict() {
        var planet = new PlanetRegistration();
        planet.setName("Tatooine");

        given().contentType(ContentType.JSON).body(planet)
                .when().post("/planets")
                .then().statusCode(HttpStatus.SC_CONFLICT);
    }

    @Test
    void addPlanet_whenMissingRequiredFields_thenBadRequest() {
        var planet = new PlanetRegistration();

        given().contentType(ContentType.JSON).body(planet)
                .when().post("/planets")
                .then().statusCode(HttpStatus.SC_BAD_REQUEST);
    }

    @Test
    void getPlanet_whenPlanetExists_thenOk() {
        var planet = getPlanetByName("Alderaan");

        var response = given().pathParam("id", planet.getId())
                .when().get("/planets/{id}");

        assertEquals(HttpStatus.SC_OK, response.statusCode());
        assertEquals(planet, response.as(Planet.class));
    }

    private Planet getPlanetByName(String planetName) {
        List<Planet> planets = given().queryParam("name", planetName)
                .when().get("/planets")
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
                .when().get("/planets/{id}")
                .then().statusCode(HttpStatus.SC_NOT_FOUND);
    }

    @Test
    void getPlanets_whenFilteringBySize_thenReturnNPlanets() {
        var size = 3;

        var response = given().queryParam("size", size)
                .when().get("/planets");

        List<Planet> planets = response.as(getPlanetListType());

        assertEquals(HttpStatus.SC_OK, response.statusCode());
        assertTrue(planets.size() <= size);
    }

    @Test
    void deletePlanet_whenPlanetExists_thenOk() {
        var planet = savePlanet();

        given().pathParam("id", planet.getId())
                .when().delete("/planets/{id}")
                .then().statusCode(HttpStatus.SC_OK);
    }

    @Test
    void deletePlanet_whenPlanetNotFound_thenNotFound() {
        var faker = new Faker();
        var planetId = faker.random().hex();

        given().pathParam("id", planetId)
                .when().delete("/planets/{id}")
                .then().statusCode(HttpStatus.SC_NOT_FOUND);
    }

    private Planet savePlanet() {
        var faker = new Faker();

        var planet = new Planet();
        planet.setName(faker.space().planet());
        planet.setClimate(faker.weather().description());

        given().contentType(ContentType.JSON).body(planet).post("/planets");

        return getPlanetByName(planet.getName());
    }
}
