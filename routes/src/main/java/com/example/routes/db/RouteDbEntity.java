package com.example.routes.db;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "routedbentity")
public class RouteDbEntity implements IRouteDbEntity {
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
    @JoinColumn(name = "from_location_id", referencedColumnName = "id")
    public LocationDbEntity FromLocation;

    @ManyToOne(targetEntity = LocationDbEntity.class)
    @JoinColumn(name = "to_location_id", referencedColumnName = "id")
    public LocationDbEntity ToLocation;


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

