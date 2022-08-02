package chapter02;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * projectName:  juc
 * packageName: chapter02
 * date: 2022-07-25 22:15
 * author 邱依良
 */
public class FutureAPIDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException, TimeoutException {
        FutureTask<String>futureTask = new FutureTask<String>(()->{
            System.out.println(Thread.currentThread().getName() + "\t come in");
            try{TimeUnit.SECONDS.sleep(5);}catch (InterruptedException e){e.printStackTrace();}
            return "task over";
        });
        Thread t1 = new Thread(futureTask, "t1");
        t1.start();

        System.out.println(Thread.currentThread().getName() + "\t 忙其他了");
//        System.out.println(futureTask.get());
//        System.out.println(futureTask.get(3,TimeUnit.SECONDS));

        while (true) {
            if (futureTask.isDone()) {
                System.out.println(futureTask.get());
                break;
            }else {
                try {
                    TimeUnit.MICROSECONDS.sleep(500);
                    System.out.println("正在处理中……");
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        }

    }
}
