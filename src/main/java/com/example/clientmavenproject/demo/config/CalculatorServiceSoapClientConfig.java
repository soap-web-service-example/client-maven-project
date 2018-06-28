package com.example.clientmavenproject.demo.config;

import com.example.clientmavenproject.demo.soapClient.AwsECommerceServiceSoapClient;
import com.example.clientmavenproject.demo.soapClient.CalculatorServiceSoapClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPConstants;
import javax.xml.soap.SOAPException;

import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.soap.SoapVersion;
import org.springframework.ws.soap.saaj.SaajSoapMessageFactory;

@Configuration
public class CalculatorServiceSoapClientConfig {

    @Value("${calculator.soap.web.service.url}")
    String calculatorServiceUrl;

    /**
     * WebServiceGatewaySupport requires Marshaller and Unmarshaller,
     * which are instances of Jaxb2Marshaller class.
     * It uses com.example.clientmavenproject.demo.soapClient as base package of the
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
        marshaller.setContextPath("com.example.clientmavenproject.demo.math.generated");
        return marshaller;
    }

    @Bean
    public CalculatorServiceSoapClient soapConnector() throws SOAPException {
        SaajSoapMessageFactory messageFactory = new SaajSoapMessageFactory(
                MessageFactory.newInstance());
        messageFactory.setSoapVersion(SoapVersion.SOAP_12);
        messageFactory.afterPropertiesSet();


        Jaxb2Marshaller marshaller = marshaller();
        CalculatorServiceSoapClient client = new CalculatorServiceSoapClient();
        client.setDefaultUri(calculatorServiceUrl);
        client.setMarshaller(marshaller);
        client.setUnmarshaller(marshaller);

        client.setMessageFactory(messageFactory);
        return client;
    }

}
