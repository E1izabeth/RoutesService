package com.example.routes.db;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface RoutesRepository extends CrudRepository<RouteDbEntity, Long> {

    // @PersistenceContext
    // private EntityManager entityManager;

    Optional<RouteDbEntity> findByDistance(Long distance);

    @Query(value = "SELECT SUM(distance) FROM routedbentity", nativeQuery = true)
    long sumDistance();
}
