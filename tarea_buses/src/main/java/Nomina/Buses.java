package Nomina;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
public class Buses {
    private @Id @GeneratedValue Long id;
    private String name_rute;
    private  String name_desvio;

    Buses() {}

    public Buses(String name_rute) {
        this.name_rute = name_rute;
    }

    public Buses(String name_rute, String name_desvio) {
        this.name_rute = name_rute;
        this.name_desvio = name_desvio;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName_rute() {
        return this.name_rute + " " + this.name_desvio;
    }

    public void setName_rute(String name) {
        String[] parts =name.split(" ");
        this.name_rute = parts[0];
        this.name_desvio = parts[1];
    }

}

