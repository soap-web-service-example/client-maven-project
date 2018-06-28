package com.example.clientmavenproject.demo.soapClient;

import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;

public class AwsECommerceServiceSoapClient extends WebServiceGatewaySupport {
    /**
     * Below method will make call to the default URL set in @link {{@link com.example.clientmavenproject.demo.config.AwsECommerceServiceSoapClientConfig#soapConnector(Jaxb2Marshaller)}}
     *
     * @param request
     * @return
     */
    public Object callWebService(Object request) {
        return getWebServiceTemplate().marshalSendAndReceive(request);
    }

    /**
     * Use below method if Url is dynamic and needs to be set at runtime
     *
     * @param url
     * @param request
     * @return
     */
    public Object callWebService(String url, Object request) {
        return getWebServiceTemplate().marshalSendAndReceive(url, request);
    }
}
