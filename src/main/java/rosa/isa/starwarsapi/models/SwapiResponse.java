package rosa.isa.starwarsapi.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SwapiResponse {
    private int count;
    private String next;
    private String previous;
    private List<SwapiPlanet> results;
}
