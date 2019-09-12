package Nomina;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.*;

import static org.springframework.hateoas.core.DummyInvocationUtils.methodOn;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@RestController
class Control_Buses {

    private final Buses_Repositorio repository;

    Control_Buses(Buses_Repositorio repository) {
        this.repository = repository;
    }

    // Aggregate root
/*
    @GetMapping("/employees")
    List<Buses> all() {
        return repository.findAll();
    }
*/
    @PostMapping("/employees")
    Buses newEmployee(@RequestBody Buses newEmployee) {
        return repository.save(newEmployee);
    }

    // Single item
/*
   @GetMapping("/buses/{id}")
    Buses one(@PathVariable Long id) {

        return repository.findById(id)
                .orElseThrow(() -> new BusNotFoundException(id));
    }
*/



    @PutMapping("/buses/{id}")
    Buses replaceEmployee(@RequestBody Buses newBus, @PathVariable Long id) {

        return repository.findById(id)
                .map(employee -> {
                    employee.setId(newBus.getId());
                    employee.setName_rute(newBus.getName_rute());
                    return repository.save(employee);
                })
                .orElseGet(() -> {
                    newBus.setId(id);
                    return repository.save(newBus);
                });
    }

    @DeleteMapping("/buses/{id}")
    void deleteEmployee(@PathVariable Long id) {
        repository.deleteById(id);
    }

    //mas

    @GetMapping("/buses/{id}")
    Resource<Buses> one(@PathVariable Long id) {

        Buses bus = repository.findById(id)
                .orElseThrow(() -> new BusNotFoundException(id));

        Resource<Buses> busees = new Resource<>(bus,
                linkTo(methodOn(Control_Buses.class).one(id)).withSelfRel(),
                linkTo(methodOn(Control_Buses.class).all()).withRel("buses"));
        return busees;
    }

    @GetMapping("/buses")
    Resources<Resource<Buses>> all() {

        List<Resource<Buses>> buses = repository.findAll().stream()
                .map(bus -> new Resource<>(bus,
                        linkTo(methodOn(Control_Buses.class).one(bus.getId())).withSelfRel(),
                        linkTo(methodOn(Control_Buses.class).all()).withRel("employees")))
                .collect(Collectors.toList());

        return new Resources<>(buses,
                linkTo(methodOn(Control_Buses.class).all()).withSelfRel());
    }



}
