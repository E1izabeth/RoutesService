package com.example.routes.db;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LocationsRepository extends CrudRepository<LocationDbEntity, Long> {

    @Query(value = "SELECT * FROM locationdbentity WHERE ABS(:x*:x - x*x) < 1 AND ABS(:z*:z - z*z) < 1 AND ABS(:y*:y - y*y) < 1 ", nativeQuery = true)
    Optional<LocationDbEntity> findNearestExisting(int x, int y, float z);

    @Query(value = "SELECT COUNT(r.id) FROM locationdbentity l LEFT OUTER JOIN routedbentity r ON r.from_location_id=l.id OR r.to_location_id=l.id WHERE l.id=:id GROUP BY l.id", nativeQuery = true)
    Optional<Integer> countUsagesById(long id);
}
