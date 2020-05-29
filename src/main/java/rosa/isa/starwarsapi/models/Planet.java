package rosa.isa.starwarsapi.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
public class Planet {
    @Id
    private String id;
    private String name;
    private String climate;
    private String terrain;

    @ReadOnlyProperty
    private Integer filmApparitions;
}
