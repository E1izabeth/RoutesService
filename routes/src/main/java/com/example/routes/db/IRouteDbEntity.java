package com.example.routes.db;

import javax.persistence.*;
import java.time.LocalDateTime;

public interface IRouteDbEntity {
    long getId();

    String getName();
    LocalDateTime getCreationDate();
    long getDistance();

    int getCoordX();
    double getCoordY();

    LocationDbEntity getLocationFrom();

    LocationDbEntity getLocationTo();
}
