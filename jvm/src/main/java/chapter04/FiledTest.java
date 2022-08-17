package chapter04;

import lombok.Data;

/**
 * @author qyl
 * @program FiledTest.java
 * @Description 字段
 * @createTime 2022-08-15 12:03
 */
public class FiledTest {
    public static void main(String[] args) {
        new FiledTest().orderTest();
    }

    public void hello(){
        System.out.println("hello");
    }

    public void orderTest(){
        Order order = new Order();
        order.setId(100);
        System.out.println(order.getId());

        order.setName("order");
        System.out.println(order.getName());
    }
    @Data
    class Order{
        private Integer id;
        private String name;
    }
}

