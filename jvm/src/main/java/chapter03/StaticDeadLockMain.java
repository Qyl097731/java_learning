package chapter03;

/**
 * @author qyl
 * @program StaticDeadLockMain.java
 * @Description 死锁
 * @createTime 2022-08-16 13:49
 */
class StaticA {
    static {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            Class.forName("chapter03.StaticB");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println("StaticB init A");
    }
}

class StaticB {
    static {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            Class.forName("chapter03.StaticA");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println("StaticB init B");
    }
}


public class StaticDeadLockMain extends Thread{
    private char flag ;

    public StaticDeadLockMain(char flag){
        this.flag = flag;
        this.setName("Thread " + flag);
    }

    @Override
    public void run() {
        try {
            Class.forName("chapter03.Static" + flag);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println(getName() + "over");
    }

    public static void main(String[] args) {
        StaticDeadLockMain loadA = new StaticDeadLockMain('A');
        loadA.start();

        StaticDeadLockMain loadB = new StaticDeadLockMain('B');
        loadB.start();
    }
}


