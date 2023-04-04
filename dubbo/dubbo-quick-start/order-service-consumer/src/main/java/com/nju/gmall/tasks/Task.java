package com.nju.gmall.tasks;

import com.nju.gmall.service.OrdersService;
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
    private OrdersService ordersService;

    @Override
    public void run(String... args) throws Exception {
         ordersService.initOrder("1");

        new Thread(()-> {
            while (true) {
                try {
                    Thread.sleep(1000);
                    ordersService.initOrder("1");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    Thread.currentThread().interrupt();
                }
            }
        }).start();
    }
}