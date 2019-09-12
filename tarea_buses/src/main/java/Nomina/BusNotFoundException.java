package Nomina;

public class BusNotFoundException extends RuntimeException {

    BusNotFoundException(Long id) {
        super("Could not find bus " + id);
    }
}