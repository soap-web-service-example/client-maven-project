package com.example.clientmavenproject.demo.soapClient;

import com.example.clientmavenproject.demo.config.AwsECommerceServiceSoapClientConfig;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.addressing.client.ActionCallback;

public class CalculatorServiceSoapClient extends WebServiceGatewaySupport {
    /**
     * Below method will make call to the default URL set in @link {{@link AwsECommerceServiceSoapClientConfig#soapConnector()}
     *
     * @param request
     * @return
     */
    public Object callWebService(Object request) {
        return getWebServiceTemplate().marshalSendAndReceive(request);
//        return getWebServiceTemplate().sendSourceAndReceiveToResult(request,new Object());
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
