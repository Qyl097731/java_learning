package chapter07;

import org.openjdk.jol.info.ClassLayout;
import org.openjdk.jol.vm.VM;

/**
 * @author qyl
 * @program JOLDemo.java
 * @Description TODO
 * @createTime 2022-08-02 15:02
 */
public class JOLDemo {
    public static void main(String[] args) {

        Object object = new Object();

        System.out.println(ClassLayout.parseInstance(object).toPrintable());

        Customer customer = new Customer();

        System.out.println(ClassLayout.parseInstance(customer).toPrintable());
    }
}

class Customer{
    int id;
    boolean flag = false;
}
