package com.qyl.ws.endpoint;

import com.qyl.ws.domain.Country;
import com.qyl.ws.domain.Currency;
import com.qyl.ws.domain.GetCountryRequest;
import com.qyl.ws.domain.GetCountryResponse;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

/**
 * country服务端口
 *
 * @author qyl
 */
@Endpoint
public class CountryEndPoint {

    private static final String NAMESPACE_URI = "http://www.qyl.com/ws";

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getCountryRequest")
    @ResponsePayload
    public GetCountryResponse getCountry(
            @RequestPayload GetCountryRequest request) {
        GetCountryResponse response = new GetCountryResponse ();
        Country poland = new Country ();
        poland.setName ("Poland-" + request.getName ());
        poland.setCapital ("Warsaw");
        poland.setCurrency (Currency.PLN);
        poland.setPopulation (38186860);
        response.setCountry (poland);
        return response;
    }
}
