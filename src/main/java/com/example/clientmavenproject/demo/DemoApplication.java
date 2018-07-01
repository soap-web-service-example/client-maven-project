package com.example.clientmavenproject.demo;

import com.example.clientmavenproject.demo.generated.math.Add;
import com.example.clientmavenproject.demo.generated.math.AddResponse;
import com.example.clientmavenproject.demo.client.soapImpl.CalculatorServiceImpl;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Bean
    CommandLineRunner lookup(CalculatorServiceImpl soapConnector) {
        return args -> {
            Add add = new Add();
            add.setIntA(10);
            add.setIntB(20);
            AddResponse response = (AddResponse) soapConnector.callWebService(add);
            System.out.println("Got Response As below ========= : ");
            System.out.println("Response : " + response);
        };
    }
}
