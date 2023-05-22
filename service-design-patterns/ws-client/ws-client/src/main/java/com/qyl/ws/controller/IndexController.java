package com.qyl.ws.controller;

import com.qyl.ws.client.WsClient;
import com.qyl.ws.domain.GetCountryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 主页controller
 * @author qyl
 */
@Controller
@RequestMapping("/")
public class IndexController {
    @Autowired
    private WsClient wsClient;

    @RequestMapping("callws")
    @ResponseBody
    public Object callWs() {
    	System.out.println("============================");
        GetCountryResponse response = wsClient.getCountry("hello");
        System.out.println("============================");
        return response.getCountry();
    }

    @RequestMapping("/index")
    @ResponseBody
    public String index() {
    	System.out.println("============world================");
    	System.out.println("============world================");
    	return "hello world";
    }
}
