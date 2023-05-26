package com.qyl.ws.config;

import com.qyl.ws.client.WsClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

/**
 * 配置文件
 * @author qyl
 */
@Configuration
public class WsConfig {
    @Bean
    public Jaxb2Marshaller marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller ();
        marshaller.setContextPath ("com.qyl.ws.domain");
        return marshaller;
    }

    @Bean
    public WsClient wsClient(Jaxb2Marshaller marshaller) {
        WsClient client = new WsClient ();
        client.setDefaultUri ("http://127.0.0.1:8080/ws/countries.wsdl");
        client.setMarshaller (marshaller);
        client.setUnmarshaller (marshaller);
        return client;
    }
}
