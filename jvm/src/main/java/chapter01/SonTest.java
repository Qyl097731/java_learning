package chapter01;

/**
 * @description 非静态成员变量
 * 默认初始化 - 显式初始化 - 构造器初始化
 * 调用属性 方法进行赋值
 * @date:2022/8/8 23:07
 * @author: qyl
 */
class Father {
    int x = 10;

    public Father() {
        this.print();
        x = 20;
    }

    public void print() {
        System.out.println("fa.x = " + x);
    }
}

class Son extends Father {
    int x = 30;

    public Son() {
        this.print();
        x = 40;
    }

    @Override
    public void print() {
        System.out.println("son.x = " + x);
    }
}

public class SonTest {
    public static void main(String[] args) {
        Father f = new Son();
        System.out.println(f.x);
    }
}
