package com.example.routes.db;

import javax.persistence.*;

@Entity
@Table(name = "locationdbentity")
public class LocationDbEntity {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long id;

    public int x;
    public int y;
    public float z;
}
