package Nomina;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
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

    private final ResourceJuntadorBuses assembler;

    Control_Buses(Buses_Repositorio repository, ResourceJuntadorBuses assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    // Aggregate root
/*
    @GetMapping("/buses")
    List<Buses> all() {
        return repository.findAll();
    }
*/
    @PostMapping("/buses")
    ResponseEntity<?> newEmployee(@RequestBody Buses newEmployee) throws URISyntaxException {

        Resource<Buses> resource = assembler.toResource(repository.save(newEmployee));

        return ResponseEntity
                .created(new URI(resource.getId().expand().getHref()))
                .body(resource);
    }

    // Single item

    // se cambio one por ones

    @GetMapping("/buses/{id}")
    Resource<Buses> ones(@PathVariable Long id) {

        Buses employee = repository.findById(id)
                .orElseThrow(() -> new BusNotFoundException(id));

        return assembler.toResource(employee);
    }


    @PutMapping("/buses/{id}")
    Buses replaceEmployee(@RequestBody Buses newBus, @PathVariable Long id) {

        return repository.findById(id)
                .map(bus -> {
                    bus.setName_rute(newBus.getName_rute());
                    return repository.save(bus);
                })
                .orElseGet(() -> {
                    newBus.setId(id);
                    return repository.save(newBus);
                });
    }


    @PutMapping("/buses/{id}")
    ResponseEntity<?> replaceBus(@RequestBody Buses newBus, @PathVariable Long id) throws URISyntaxException {

        Buses updatedEmployee = repository.findById(id)
                .map(bus -> {
                    bus.setName_rute(newBus.getName_rute());
                    return repository.save(bus);
                })
                .orElseGet(() -> {
                    newBus.setId(id);
                    return repository.save(newBus);
                });

        Resource<Buses> resource = assembler.toResource(updatedEmployee);

        return ResponseEntity
                .created(new URI(resource.getId().expand().getHref()))
                .body(resource);
    }




    @DeleteMapping("/buses/{id}")
    void deleteBusess(@PathVariable Long id) {
        repository.deleteById(id);
    }

    @DeleteMapping("/buses/{id}")
    ResponseEntity<?> deleteBuses (@PathVariable Long id) {

        repository.deleteById(id);

        return ResponseEntity.noContent().build();
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
                .map(assembler::toResource)
                .collect(Collectors.toList());

        return new Resources<>(buses,
                linkTo(methodOn(Control_Buses.class).all()).withSelfRel());
    }



}
