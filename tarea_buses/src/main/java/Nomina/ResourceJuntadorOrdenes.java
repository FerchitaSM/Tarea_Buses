package Nomina;


import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;
 import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

@Component
class ResourceJuntadorOrdenes implements ResourceAssembler<Orden, Resource<Orden>> {

    @Override
    public Resource<Orden> toResource(Orden order) {

        // Unconditional links to single-item resource and aggregate root

        Resource<Orden> orderResource = new Resource<>(order,
                linkTo(methodOn(Controlador_Orden.class).one(order.getId())).withSelfRel(),
                linkTo(methodOn(Controlador_Orden.class).all()).withRel("ordenes")
        );

        // Conditional links based on state of the order

        if (order.getStatus() == Status.IN_PROGRESS) {
            orderResource.add(
                    linkTo(methodOn(Controlador_Orden.class)
                            .cancel(order.getId())).withRel("cancel"));
            orderResource.add(
                    linkTo(methodOn(Controlador_Orden.class)
                            .complete(order.getId())).withRel("complete"));
        }

        return orderResource;
    }
}