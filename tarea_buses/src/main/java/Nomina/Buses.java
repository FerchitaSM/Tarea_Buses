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
    Buses() {}

    public Buses(String name_rute) {
        this.name_rute = name_rute;
    }

}