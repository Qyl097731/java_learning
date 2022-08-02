package chapter04;

import java.util.concurrent.TimeUnit;

/**
 * projectName:  juc
 * packageName: chapter04
 *
 * @author 邱依良
 * @date: 2022-07-26 23:58
 */
class Phone {
    public static synchronized void sendEmail() {
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("----sendEmail");
    }

    public synchronized void sendSMS(){
        System.out.println("------sendSMS");
    }

    public void hello(){
        System.out.println("-------hello");
    }
}
/**
 * 题目：八锁案例
 * 1.标准访问ab线程,先邮件还是短信
 * 2.邮件暂停三秒,先邮件还是短信
 * 3.添加一个普通hello 先邮件还是hello
 * 4.两部手机，先邮件还是短信
 * 5.两个静态同步方法，有一部手机，先邮件还是短信
 * 6.有两个静态同步方法，有两部手机，先邮件还是短信
 * 7.有一个静态同步，一个普通同步方法，一部手机，先邮件还是短信
 * 8.有一个静态同步，一个普通同步方法，两部部手机，先邮件还是短信
**/
public class Lock8Demo {
    public static void main(String[] args) {
        Phone phone1 = new Phone();
        Phone phone2 = new Phone();;

        new Thread(()->{
            phone1.sendEmail();
        },"a").start();

        try{TimeUnit.MICROSECONDS.sleep(200);}catch (InterruptedException e){ e.printStackTrace(); }

        new Thread(()->{
            phone2.sendSMS();
//            phone2.hello();
        },"b").start();
    }
}
