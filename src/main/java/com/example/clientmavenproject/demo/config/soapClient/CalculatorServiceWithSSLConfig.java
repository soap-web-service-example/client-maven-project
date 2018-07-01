package com.example.clientmavenproject.demo.config.soapClient;

import com.example.clientmavenproject.demo.client.soapImpl.CalculatorServiceImpl;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.Header;
import org.apache.http.HttpHeaders;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;
import org.apache.http.ssl.SSLContextBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.Resource;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.transport.http.HttpComponentsMessageSender;
import org.springframework.ws.transport.http.HttpComponentsMessageSender.RemoveSoapHeadersInterceptor;

import javax.net.ssl.SSLContext;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


/**
 * Add dependency for -- httpclient -->
 * <dependency>
 * <groupId>org.apache.httpcomponents</groupId>
 * <artifactId>httpclient</artifactId>
 * <version>${httpclient.version}</version>
 * </dependency>
 */
@Profile(value = {"enableSSL"})
@Configuration
public class CalculatorServiceWithSSLConfig {

    @Value("${calculator.soap.web.service.url}")
    String calculatorServiceUrl;

    @Value("${calculator.soap.web.service.userName}")
    private String userName;

    @Value("${calculator.soap.web.service.userPassword}")
    private String userPassword;

    @Value("${calculator.soap.web.service.ssl.trust-store}")
    private Resource trustStore;

    @Value("${calculator.soap.web.service.ssl.trust-store-password}")
    private String trustStorePassword;


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
    private Jaxb2Marshaller marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        // this is the package name specified in the <generatePackage> specified in
        // pom.xml
        marshaller.setContextPath("com.example.clientmavenproject.demo.generated.math");
//        marshaller.setContextPath(ObjectFactory.class.getPackage().getName());
        return marshaller;
    }

    @Bean
    public CalculatorServiceImpl soapConnector() throws Exception {
        CalculatorServiceImpl calculatorService = new CalculatorServiceImpl();
        Jaxb2Marshaller jaxb2Marshaller = marshaller();
        calculatorService.setMarshaller(jaxb2Marshaller);
        calculatorService.setUnmarshaller(jaxb2Marshaller);
        calculatorService.setDefaultUri(calculatorServiceUrl);
        // set a HttpComponentsMessageSender which provides support for basic authentication
        calculatorService.setMessageSender(httpComponentsMessageSender());

        return calculatorService;
    }

    private HttpComponentsMessageSender httpComponentsMessageSender() throws Exception {
        HttpComponentsMessageSender httpComponentsMessageSender = new HttpComponentsMessageSender();
        // setting of the basic authorization credentials using
        // httpComponentsMessageSender will not work here because
        // It will be overridden by httpComponentsMessageSender.setHttpClient(httpClient())
        // So if needs to set authentication then set it at HttpClient label.
//        httpComponentsMessageSender.setCredentials(usernamePasswordCredentials());
        httpComponentsMessageSender.setHttpClient(httpClient());
        return httpComponentsMessageSender;
    }

    private HttpClient httpClient() throws Exception {
        return HttpClientBuilder.create()
                .setSSLSocketFactory(sslConnectionSocketFactory())
                // If there is no credential required, then leave it
                // Read more at- @link http://www.baeldung.com/httpclient-4-basic-authentication
                .setDefaultCredentialsProvider(credentialsProvider())
                .setDefaultHeaders(defaultHeaders())
                .addInterceptorFirst(new RemoveSoapHeadersInterceptor()).build();
    }

    private Collection<? extends Header> defaultHeaders() {
        List<BasicHeader> basicHeaders = new ArrayList<>();
        // There are cases where wee need to send Basic Authentication in header, we can do as follow
        basicHeaders.add(createBase64EncodedAuthenticationHeader());
        basicHeaders.add(new BasicHeader(HttpHeaders.FROM, "Source from"));
        basicHeaders.add(new BasicHeader("headerKey1", "HeaderValue1"));
        basicHeaders.add(new BasicHeader("headerKey2", "HeaderValue2"));
        return basicHeaders;
    }

    private BasicHeader createBase64EncodedAuthenticationHeader() {
        String auth = "DEFAULT_USER : DEFAULT_PASS";
        byte[] encodedAuth = Base64.encodeBase64(
                auth.getBytes(StandardCharsets.ISO_8859_1));
        String authHeaderValue = "Basic " + new String(encodedAuth);
        return new BasicHeader(HttpHeaders.AUTHORIZATION, authHeaderValue);
    }

    private CredentialsProvider credentialsProvider() {
        BasicCredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY, usernamePasswordCredentials());
        return credentialsProvider;
    }

    private UsernamePasswordCredentials usernamePasswordCredentials() {
        // pass the user name and password to be used
        return new UsernamePasswordCredentials(userName, userPassword);
    }

    private SSLConnectionSocketFactory sslConnectionSocketFactory() throws Exception {
        // NoopHostnameVerifier essentially turns hostname verification off as otherwise following error
        // is thrown: java.security.cert.CertificateException: No name matching localhost found
        return new SSLConnectionSocketFactory(sslContext(), NoopHostnameVerifier.INSTANCE);
    }

    private SSLContext sslContext() throws Exception {
        return SSLContextBuilder.create()
                .loadTrustMaterial(trustStore.getFile(), trustStorePassword.toCharArray()).build();
    }
}
