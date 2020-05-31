package rosa.isa.starwarsapi.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SwapiResponse {
    private int count;
    private String next;
    private String previous;
    private List<SwapiPlanet> results;
}
