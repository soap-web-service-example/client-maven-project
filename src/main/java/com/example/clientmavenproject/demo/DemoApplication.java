package com.example.clientmavenproject.demo;

import com.example.clientmavenproject.demo.math.generated.Add;
import com.example.clientmavenproject.demo.math.generated.AddResponse;
import com.example.clientmavenproject.demo.soapClient.CalculatorServiceSoapClient;
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
    CommandLineRunner lookup(CalculatorServiceSoapClient soapConnector) {
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
