package chapter04;

import java.util.concurrent.TimeUnit;

/**
 * @description
 * @date:2022/7/31 15:21
 * @author: qyl
 */
public class InterruptDemo3 {
	public static void main(String[] args) {
		Thread t1 = new Thread(() -> {
			while (true) {
				if (Thread.currentThread().isInterrupted()) {
					System.out.println(Thread.currentThread().getName() + "\t 中断标志位:" + Thread.currentThread().isInterrupted() + " 程序停止");
					break;
				}
				//sleep之后interrupt状态被清除 所以抛出异常 继续死循环
				try {
					TimeUnit.MICROSECONDS.sleep(100);
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
					e.printStackTrace();
				}
				System.out.println("------ hello InterruptDemo3");
			}
		}, "t1");
		t1.start();


		try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		new Thread(() -> t1.interrupt(), "t2").start();
	}
}
