package com.example.routes.db;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LocatedRoutesRepository extends CrudRepository<LocatedRouteDbEntity, Long> {

    List<LocatedRouteDbEntity> findByDistance(Long distance);

    @Query(value = "SELECT SUM(distance) FROM routedbentity", nativeQuery = true)
    long sumDistance();

    Page<LocatedRouteDbEntity> findAll(Specification<LocatedRouteDbEntity> specification, Pageable pageable);
}
