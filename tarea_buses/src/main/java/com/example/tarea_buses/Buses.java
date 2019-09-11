package com.example.tarea_buses;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
public class Buses {
    private @Id @GeneratedValue Long id;
    private String name_rute;
    private boolean going;
    private String[] stops;

    Buses() {}

    public Buses(String name_rute, boolean going, String[] stops) {
        this.name_rute = name_rute;
        this.going = going;
        this.stops = stops;
    }

}