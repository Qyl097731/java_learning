package chapter04;

import lombok.Data;

/**
 * @description 字段学习
 * @date:2022/8/15 20:59
 * @author: qyl
 */
public class FieldTest {
    public void setOrderId() {
        Order order = new Order();
        order.age = 1;
        System.out.println(order.age);

        Order.name = "order";
        System.out.println(Order.name);
    }

    public void setArray(){
        int[] intArray = new int[10];
        intArray[3] = 20;
        System.out.println(intArray[1]);

        boolean[] arr = new boolean[10];
        arr[1] = true;
    }

    public void arrLen(){
        double[] arr = new double[20];
        System.out.println(arr.length);
    }

    public String checkCast(Object object){
        if (object instanceof String) {
            return (String)object;
        }
        return null;
    }

    public void sayHello() {
        System.out.println("hello");
    }
}

class Order {
    int age;
    static String name;

}
