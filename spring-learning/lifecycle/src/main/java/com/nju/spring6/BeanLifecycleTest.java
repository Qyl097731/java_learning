package com.nju.spring6;

import com.nju.spring6.bean.User1;
import com.nju.spring6.bean.User2;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class BeanLifecycleTest {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring.xml");
        User1 user1Bean = applicationContext.getBean("user1Bean", User1.class);
        System.out.println("4.使用Bean");
        // 只有正常关闭spring容器才会执行销毁方法
        ClassPathXmlApplicationContext context = (ClassPathXmlApplicationContext) applicationContext;
        context.close();

//        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring.xml");
//        User2 user2Bean = applicationContext.getBean("user2Bean", User2.class);
//        // 只有正常关闭spring容器才会执行销毁方法
//        ClassPathXmlApplicationContext context = (ClassPathXmlApplicationContext) applicationContext;
//        context.close();
    }
}