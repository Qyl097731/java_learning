package chapter03;

import org.junit.Test;

import java.io.*;

/**
 * @description
 * @date:2022/8/16 21:33
 * @author: qyl
 */
public class InitiativeUse {
    public static void main(String[] args) {
        Order order = new Order();
    }

    @Test
    public void test1(){
        try(ObjectOutputStream oos =
                    new ObjectOutputStream(new FileOutputStream("order.txt"))){
            oos.writeObject(new Order());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test2(){
        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream("order.txt"))){
            Order order = (Order)ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test3(){
        Order.method();
    }
}

class Order implements Serializable{
    static {
        System.out.println("你好");
    }

    public static void method(){
        System.out.println("static method");
    }
}
