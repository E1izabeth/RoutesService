package main.webapp;

import main.webapp.db.DbField;

import java.time.LocalDateTime;

public class RouteDbEntity {

    @DbField(dbType = "bigserial PRIMARY KEY NOT NULL", isDbGenerated = true, isReadOnly = true)
    public long id;

    public String name;
    @DbField(isReadOnly = true)
    public LocalDateTime creationDate;
    public long distance;

    public int coordX;
    public double coordY;

    public int fromLocX;
    public int fromLocY;
    public float fromLocZ;

    public int toLocX;
    public int toLocY;
    public float toLocZ;
}
