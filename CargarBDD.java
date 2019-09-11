package com.example.tarea_buses;


import lombok.extern.slf4j.Slf4j;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
class LoadDatabase {

    @Bean
    CommandLineRunner initDatabase(BusesRepository repository) {
        return args -> {
            log.info("Preloading " + repository.save(new Buses("Bilbo Baggins", true,null)));
            log.info("Preloading " + repository.save(new Buses("Frodo Baggins", false,null)));
        };
    }
}