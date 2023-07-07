package com.nju.spring6;

import com.nju.spring6.bean.Husband;
import com.nju.spring6.bean.Wife;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class RecycleTest {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring.xml");
        Husband husbandBean = applicationContext.getBean("husbandBean", Husband.class);
        Wife wifeBean = applicationContext.getBean("wifeBean", Wife.class);
        System.out.println(husbandBean);
        System.out.println(wifeBean);

        // 有错
        Husband husbandBean1 = applicationContext.getBean("husbandBean1", Husband.class);
        Wife wifeBean1 = applicationContext.getBean("wifeBean1", Wife.class);
        System.out.println(husbandBean1);
        System.out.println(wifeBean1);
    }
}
