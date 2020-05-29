package rosa.isa.starwarsapi.models;

import lombok.Data;

@Data
public class Planet {
    private String id;
    private String name;
    private String climate;
    private String terrain;
}
