package Nomina;


import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

    @Component
    class Buses_Assemble_Resource implements ResourceAssembler<Buses, Resource<Buses>> {

        @Override
        public Resource<Buses> toResource(Buses bus) {

            return new Resource<>(bus,
                    linkTo(methodOn(Buses_Controlador.class).one(bus.getId())).withSelfRel(),
                    linkTo(methodOn(Buses_Controlador.class).all()).withRel("buses"));
        }
    }