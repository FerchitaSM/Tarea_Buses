package Nomina;


import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "CUSTOMER_ORDER")
public class Orden {

    private @Id @GeneratedValue Long id;

    private String description;
    private Orden_Status status;

    Orden() {}

    public Orden(String description, Orden_Status status) {

        this.description = description;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Orden_Status getStatus() {
        return status;
    }

    public void setStatus(Orden_Status status) {
        this.status = status;
    }
}