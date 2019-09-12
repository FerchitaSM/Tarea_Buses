package Nomina;

import lombok.extern.slf4j.Slf4j;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

@Configuration


@Slf4j
class Caegar_BDD {
    Logger log = LoggerFactory.getLogger (Nomina_Buses.class);

    @Bean
    CommandLineRunner initDatabase(Buses_Repositorio repository) {
        return args -> {
           log.info("PPPPPPPPPPPP " + repository.save(new Buses( "IncaLlojeta")));

        };
    }
}