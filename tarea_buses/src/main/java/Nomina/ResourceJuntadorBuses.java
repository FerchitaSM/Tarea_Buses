package Nomina;


import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

    @Component
    class ResourceJuntadorBuses implements ResourceAssembler<Buses, Resource<Buses>> {

        @Override
        public Resource<Buses> toResource(Buses bus) {

            return new Resource<>(bus,
                    linkTo(methodOn(Control_Buses.class).one(bus.getId())).withSelfRel(),
                    linkTo(methodOn(Control_Buses.class).all()).withRel("buses"));
        }
    }