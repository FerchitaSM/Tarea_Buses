package Nomina;

public class OrdenNotFoundException extends RuntimeException {

    OrdenNotFoundException(Long id) {
        super("Could not find bus " + id);
    }
}