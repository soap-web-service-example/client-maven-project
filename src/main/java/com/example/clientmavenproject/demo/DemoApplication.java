package com.example.clientmavenproject.demo;

import com.example.clientmavenproject.demo.client.CalculatorService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    //    @Bean
    CommandLineRunner lookup(CalculatorService calculatorService) {
        return args -> {
            System.out.println("Got Response As below ========= : ");
            System.out.println("Response : " + calculatorService.add(10, 20));
        };
    }
}
