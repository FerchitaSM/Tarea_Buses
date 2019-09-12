package Nomina;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.VndErrors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.core.DummyInvocationUtils.methodOn;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@RestController
class Orden_Controlador {

    private final Orden_Repositorio orderRepository;
    private final Orden_Assembler_Resource assembler;

    Orden_Controlador(Orden_Repositorio orderRepository,
                      Orden_Assembler_Resource assembler) {

        this.orderRepository = orderRepository;
        this.assembler = assembler;
    }

    @GetMapping("/orders")
    Resources<Resource<Orden>> all() {

        List<Resource<Orden>> orders = orderRepository.findAll().stream()
                .map(assembler::toResource)
                .collect(Collectors.toList());

        return new Resources<>(orders,
                linkTo(methodOn(Orden_Controlador.class).all()).withSelfRel());
    }

    @GetMapping("/orders/{id}")
    Resource<Orden> one(@PathVariable Long id) {
        return assembler.toResource(
                orderRepository.findById(id)
                        .orElseThrow(() -> new OrdenNotFoundException(id)));
    }

    @PostMapping("/orders")
    ResponseEntity<Resource<Orden>> newOrder(@RequestBody Orden orden) {

        orden.setStatus(Orden_Status.IN_PROGRESS);
        Orden newOrder = orderRepository.save(orden);

        return ResponseEntity
                .created(linkTo(methodOn(Orden_Controlador.class).one(newOrder.getId())).toUri())
                .body(assembler.toResource(newOrder));
    }


    @DeleteMapping("/orders/{id}/cancel")
    ResponseEntity<ResourceSupport> cancel(@PathVariable Long id) {

        Orden orden = orderRepository.findById(id).orElseThrow(() -> new OrdenNotFoundException(id));

        if (orden.getStatus() == Orden_Status.IN_PROGRESS) {
            orden.setStatus(Orden_Status.CANCELLED);
            return ResponseEntity.ok(assembler.toResource(orderRepository.save(orden)));
        }

        return ResponseEntity
                .status(HttpStatus.METHOD_NOT_ALLOWED)
                .body(new VndErrors.VndError("Method not allowed", "You can't cancel an order that is in the " + orden.getStatus() + " status"));
    }


    @PutMapping("/orders/{id}/complete")
    ResponseEntity<ResourceSupport> complete(@PathVariable Long id) {

        Orden orden  = orderRepository.findById(id).orElseThrow(() -> new OrdenNotFoundException(id));

        if (orden.getStatus() == Orden_Status.IN_PROGRESS) {
            orden.setStatus(Orden_Status.COMPLETED);
            return ResponseEntity.ok(assembler.toResource(orderRepository.save(orden)));
        }

        return ResponseEntity
                .status(HttpStatus.METHOD_NOT_ALLOWED)
                .body(new VndErrors.VndError("Method not allowed", "You can't complete an order that is in the " + orden.getStatus() + " status"));
    }
}