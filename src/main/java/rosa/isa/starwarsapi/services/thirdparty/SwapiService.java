package rosa.isa.starwarsapi.services.thirdparty;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import rosa.isa.starwarsapi.models.SwapiResponse;

@FeignClient(name = "swapi", url = "${swapi.url}")
public interface SwapiService {

    @GetMapping("/planets/?search={name}")
    SwapiResponse getPlanetsByName(@PathVariable("name") String name);
}