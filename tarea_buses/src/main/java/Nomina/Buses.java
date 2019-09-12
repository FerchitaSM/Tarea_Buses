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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName_rute() {
        return name_rute;
    }

    public void setName_rute(String name_rute) {
        this.name_rute = name_rute;
    }
}