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
class Controlador_Orden {

    private final OrderRepository orderRepository;
    private final ResourceJuntadorOrdenes assembler;

    Controlador_Orden(OrderRepository orderRepository,
                      ResourceJuntadorOrdenes assembler) {

        this.orderRepository = orderRepository;
        this.assembler = assembler;
    }

    @GetMapping("/orders")
    Resources<Resource<Orden>> all() {

        List<Resource<Orden>> orders = orderRepository.findAll().stream()
                .map(assembler::toResource)
                .collect(Collectors.toList());

        return new Resources<>(orders,
                linkTo(methodOn(Controlador_Orden.class).all()).withSelfRel());
    }

    @GetMapping("/orders/{id}")
    Resource<Orden> one(@PathVariable Long id) {
        return assembler.toResource(
                orderRepository.findById(id)
                        .orElseThrow(() -> new OrdenNotFoundException(id)));
    }

    @PostMapping("/orders")
    ResponseEntity<Resource<Orden>> newOrder(@RequestBody Orden orden) {

        orden.setStatus(Status.IN_PROGRESS);
        Orden newOrder = orderRepository.save(orden);

        return ResponseEntity
                .created(linkTo(methodOn(Controlador_Orden.class).one(newOrder.getId())).toUri())
                .body(assembler.toResource(newOrder));
    }


    @DeleteMapping("/orders/{id}/cancel")
    ResponseEntity<ResourceSupport> cancel(@PathVariable Long id) {

        Orden orden = orderRepository.findById(id).orElseThrow(() -> new OrdenNotFoundException(id));

        if (orden.getStatus() == Status.IN_PROGRESS) {
            orden.setStatus(Status.CANCELLED);
            return ResponseEntity.ok(assembler.toResource(orderRepository.save(orden)));
        }

        return ResponseEntity
                .status(HttpStatus.METHOD_NOT_ALLOWED)
                .body(new VndErrors.VndError("Method not allowed", "You can't cancel an order that is in the " + orden.getStatus() + " status"));
    }


    @PutMapping("/orders/{id}/complete")
    ResponseEntity<ResourceSupport> complete(@PathVariable Long id) {

        Orden orden  = orderRepository.findById(id).orElseThrow(() -> new OrdenNotFoundException(id));

        if (orden.getStatus() == Status.IN_PROGRESS) {
            orden.setStatus(Status.COMPLETED);
            return ResponseEntity.ok(assembler.toResource(orderRepository.save(orden)));
        }

        return ResponseEntity
                .status(HttpStatus.METHOD_NOT_ALLOWED)
                .body(new VndErrors.VndError("Method not allowed", "You can't complete an order that is in the " + orden.getStatus() + " status"));
    }
}