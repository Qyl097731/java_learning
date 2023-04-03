package com.nju.gmall.tasks;

import com.nju.gmall.service.OrderService;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * @description
 * @date 2023/4/4 1:22
 * @author: qyl
 */
@Component
public class Task implements CommandLineRunner {
    @Autowired
    OrderService orderService;

    @Override
    public void run(String... args) throws Exception {
        orderService.initOrder ("1");
        new Thread (()->{
            while (true){
                try {
                    Thread.sleep (1000);
                }catch (InterruptedException e){
                    e.printStackTrace ();
                    Thread.currentThread ().interrupt ();
                }
            }
        }).start ();
    }
}
