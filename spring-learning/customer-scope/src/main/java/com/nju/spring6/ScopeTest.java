package com.nju.spring6;

import com.nju.spring6.beans.SpringBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @description 自定义scope测试
 * @date 2023/7/7 0:41
 * @author: qyl
 */
public class ScopeTest {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext ("spring-scope.xml");
        SpringBean sb1 = applicationContext.getBean("sb", SpringBean.class);
        SpringBean sb2 = applicationContext.getBean("sb", SpringBean.class);
        System.out.println(sb1);
        System.out.println(sb2);
        // 启动线程
        new Thread(new Runnable() {
            @Override
            public void run() {
                SpringBean a = applicationContext.getBean("sb", SpringBean.class);
                SpringBean b = applicationContext.getBean("sb", SpringBean.class);
                System.out.println(a);
                System.out.println(b);
            }
        }).start();
    }
}
