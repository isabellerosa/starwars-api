package rosa.isa.starwarsapi.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import rosa.isa.starwarsapi.models.Planet;

@Repository
public interface PlanetRepository extends MongoRepository<Planet, Integer> {
    Planet save(Planet planet);

    Planet findById(String id);

    Planet findByName(String name);

    Page<Planet> findAll(Pageable pageable);

    Planet deleteById(String id);
}
