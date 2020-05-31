package rosa.isa.starwarsapi.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlanetRegistration {
    @NotBlank(message = "Field name is required")
    private String name;

    @NotBlank(message = "Field climate is required")
    private String climate;

    @NotBlank(message = "Field terrain is required")
    private String terrain;
}
