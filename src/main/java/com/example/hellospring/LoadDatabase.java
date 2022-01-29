package com.example.hellospring;

import com.example.hellospring.entity.Employee;
import com.example.hellospring.repository.EmployeeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoadDatabase {

    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDatabase(EmployeeRepository repository){
        return args -> {
            log.info("Preloading " + repository.save(new Employee("Elmir Ahadov", "Developer", "elmir.ahadov@yahoo.com")));
            log.info("Preloading " + repository.save(new Employee("John Smith", "Manager", "john.smith@gmail.com")));
        };
    }

}
