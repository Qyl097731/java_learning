package com.qyl.ws.client;

import com.qyl.ws.domain.GetCountryRequest;
import com.qyl.ws.domain.GetCountryResponse;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;

/**
 * @description ws客户端
 * @date 2023/5/22 23:26
 * @author: qyl
 */
public class WsClient extends WebServiceGatewaySupport {
    public GetCountryResponse getCountry(String name) {
        GetCountryRequest request = new GetCountryRequest();
        request.setName(name);
        GetCountryResponse response = (GetCountryResponse) getWebServiceTemplate()
                .marshalSendAndReceive(
                        "http://127.0.0.1:8080/ws/countries.wsdl",
                        request);
        return response;
    }
}
