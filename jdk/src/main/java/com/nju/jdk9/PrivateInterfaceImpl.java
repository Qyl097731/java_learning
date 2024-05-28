package com.nju.jdk9;

/**
 * @description 接口允许私有方法
 * @date 2024/5/20 19:12
 * @author: qyl
 */
interface MyInterface {
    default void doSomething() {
        sayHello ();
        sayGoodBye();
    }

    void sayGoodBye();

    private void sayHello() {
        System.out.println ("hello");
    }
}

public class PrivateInterfaceImpl implements MyInterface{
    public static void main(String[] args) {
        PrivateInterfaceImpl impl = new PrivateInterfaceImpl ();
        impl.doSomething ();
    }

    @Override
    public void sayGoodBye() {
        System.out.println ("bye");
    }
}
