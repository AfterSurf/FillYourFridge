package com.example.own_api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoadDatabase {

    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDatabase(FoodRepository foodRepository) {
        return args -> {
            foodRepository.save(new Food("Tomate", 1.2));
            foodRepository.save(new Food("Apfel", 3.0));

            foodRepository.findAll().forEach(food -> log.info("Preloaded " + food));
        };

    };

}
