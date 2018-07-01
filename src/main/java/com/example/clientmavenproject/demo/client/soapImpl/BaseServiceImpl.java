package com.example.clientmavenproject.demo.client.soapImpl;

import com.example.clientmavenproject.demo.config.soapClient.CalculatorServiceConfig;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;

public class BaseServiceImpl extends WebServiceGatewaySupport {
    /**
     * Below method will make call to the default URL set in @link {{@link CalculatorServiceConfig}
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
