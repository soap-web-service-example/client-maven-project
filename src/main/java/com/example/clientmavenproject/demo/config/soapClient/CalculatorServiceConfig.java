package com.example.clientmavenproject.demo.config.soapClient;

import com.example.clientmavenproject.demo.client.soapImpl.CalculatorServiceImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPException;

import org.springframework.ws.soap.SoapVersion;
import org.springframework.ws.soap.saaj.SaajSoapMessageFactory;

@Configuration
public class CalculatorServiceConfig {

    @Value("${calculator.soap.web.service.url}")
    String calculatorServiceUrl;

    /**
     * WebServiceGatewaySupport requires Marshaller and Unmarshaller,
     * which are instances of Jaxb2Marshaller class.
     * It uses com.example.clientmavenproject.demo.client.soapClient as base package of the
     * JAXB classes. It will use this package to create the JAXB context.
     * We will use this Jaxb2Marshaller bean as Marshaller/Unmarshaller of SOAPConnector bean.
     *
     * @param
     * @return
     */
    public Jaxb2Marshaller marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        // this is the package name specified in the <generatePackage> specified in
        // pom.xml
        marshaller.setContextPath("com.example.clientmavenproject.demo.generated.math");
//        marshaller.setContextPath(ObjectFactory.class.getPackage().getName());
        return marshaller;
    }

    @Bean
    public CalculatorServiceImpl soapConnector() throws SOAPException {
        Jaxb2Marshaller marshaller = marshaller();
        CalculatorServiceImpl client = new CalculatorServiceImpl();
        client.setDefaultUri(calculatorServiceUrl);
        client.setMarshaller(marshaller);
        client.setUnmarshaller(marshaller);
        return client;
    }

}
