package com.example.clientmavenproject.demo.config.soapClient;

import com.example.clientmavenproject.demo.client.soapImpl.CalculatorServiceImpl;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.transport.http.HttpComponentsMessageSender;

import javax.xml.soap.SOAPException;


/**
 * Add dependency for -- httpclient -->
 * <dependency>
 * <groupId>org.apache.httpcomponents</groupId>
 * <artifactId>httpclient</artifactId>
 * <version>${httpclient.version}</version>
 * </dependency>
 */
@Profile(value = {"enableAuthentication"})
@Configuration
public class CalculatorServiceWithAuthenticationConfig {

    @Value("${calculator.soap.web.service.url}")
    String calculatorServiceUrl;

    @Value("${calculator.soap.web.service.userName}")
    private String userName;

    @Value("${calculator.soap.web.service.userPassword}")
    private String userPassword;

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
        CalculatorServiceImpl calculatorService = new CalculatorServiceImpl();
        Jaxb2Marshaller jaxb2Marshaller = marshaller();
        calculatorService.setUnmarshaller(jaxb2Marshaller);
        calculatorService.setMarshaller(jaxb2Marshaller);
        calculatorService.setDefaultUri(calculatorServiceUrl);
        // set a HttpComponentsMessageSender which provides support for basic authentication
        calculatorService.setMessageSender(httpComponentsMessageSender());

        return calculatorService;
    }

    private HttpComponentsMessageSender httpComponentsMessageSender() {
        HttpComponentsMessageSender httpComponentsMessageSender = new HttpComponentsMessageSender();
        // set the basic authorization credentials
        httpComponentsMessageSender.setCredentials(usernamePasswordCredentials());
        // We can set other component here eg:-
        httpComponentsMessageSender.setConnectionTimeout(10);
        return httpComponentsMessageSender;
    }

    private UsernamePasswordCredentials usernamePasswordCredentials() {
        // pass the user name and password to be used
        return new UsernamePasswordCredentials(userName, userPassword);
    }
}
