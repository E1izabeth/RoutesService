package com.example.routes.db;

import org.hibernate.annotations.Subselect;
import org.springframework.data.annotation.Immutable;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Immutable
@Subselect(value = "SELECT r.*, f.x as from_x, f.y as from_y, f.z as from_z, t.x as to_x, t.y as to_y, t.z as to_z, r.from_location_id as from_id, r.to_location_id as to_id FROM routedbentity r INNER JOIN locationdbentity f ON f.id = from_location_id INNER JOIN locationdbentity t ON t.id = to_location_id")
public class LocatedRouteDbEntity implements IRouteDbEntity {
    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long id;

    public String name;
    public LocalDateTime creationDate;
    public long distance;

    public int coordX;
    public double coordY;

    @ManyToOne(targetEntity = LocationDbEntity.class)
    @JoinColumn(name = "from_id", referencedColumnName = "id")
    public LocationDbEntity FromLocation;

    @ManyToOne(targetEntity = LocationDbEntity.class)
    @JoinColumn(name = "to_id", referencedColumnName = "id")
    public LocationDbEntity ToLocation;

    public long from_location_id;
    public long to_location_id;

    public int from_x;
    public int from_y;
    public float from_z;

    public int to_x;
    public int to_y;
    public float to_z;

    @Override
    @org.springframework.data.annotation.Transient
    public long getId() {
        return this.id;
    }

    @Override
    @org.springframework.data.annotation.Transient
    public String getName() {
        return this.name;
    }

    @Override
    @org.springframework.data.annotation.Transient
    public LocalDateTime getCreationDate() {
        return this.creationDate;
    }

    @Override
    @org.springframework.data.annotation.Transient
    public long getDistance() {
        return this.distance;
    }

    @Override
    @org.springframework.data.annotation.Transient
    public int getCoordX() {
        return this.coordX;
    }

    @Override
    @org.springframework.data.annotation.Transient
    public double getCoordY() {
        return this.coordY;
    }

    @Override
    @org.springframework.data.annotation.Transient
    public LocationDbEntity getLocationFrom() {
        return this.FromLocation;
    }

    @Override
    @org.springframework.data.annotation.Transient
    public LocationDbEntity getLocationTo() {
        return this.ToLocation;
    }
}

