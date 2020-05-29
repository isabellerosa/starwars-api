package rosa.isa.starwarsapi.models;

import lombok.Data;

import java.util.List;

@Data
public class SwapiPlanet {
    private String name;
    private List<String> films;
}
