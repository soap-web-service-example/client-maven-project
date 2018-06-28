package com.example.clientmavenproject.demo.config;

import com.example.clientmavenproject.demo.soapClient.AwsECommerceServiceSoapClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

@Configuration
public class AwsECommerceServiceSoapClientConfig {

    @Value("${aws.e.commerce.soap.web.service.url}")
    String globalWeatherSoapWebServiceUrl;

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
        marshaller.setContextPath("com.example.clientmavenproject.demo.aws.generated");
        return marshaller;
    }

    @Bean
    public AwsECommerceServiceSoapClient soapConnector() {
        Jaxb2Marshaller marshaller = marshaller();
        AwsECommerceServiceSoapClient client = new AwsECommerceServiceSoapClient();
        client.setDefaultUri(globalWeatherSoapWebServiceUrl);
        client.setMarshaller(marshaller);
        client.setUnmarshaller(marshaller);
        return client;
    }

}
