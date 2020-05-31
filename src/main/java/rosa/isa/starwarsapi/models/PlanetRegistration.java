package rosa.isa.starwarsapi.models;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class PlanetRegistration {
    @NotBlank(message = "Field name is required")
    private String name;
    private String climate;
    private String terrain;
}
