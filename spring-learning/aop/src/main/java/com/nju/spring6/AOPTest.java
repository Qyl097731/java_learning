package com.nju.spring6;

import com.nju.spring6.service.OrderService;
import com.nju.spring6.service.Spring6Configuration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author asus
 */
public class AOPTest {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext ("spring-aspectj-aop-annotation.xml");
        OrderService orderService = applicationContext.getBean("orderService", OrderService.class);
        orderService.generate();

        applicationContext = new AnnotationConfigApplicationContext (Spring6Configuration.class);
        orderService = applicationContext.getBean("orderService", OrderService.class);
        orderService.generate();
    }
}
