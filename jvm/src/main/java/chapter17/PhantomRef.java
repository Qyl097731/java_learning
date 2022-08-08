package chapter17;

import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;

/**
 * @description
 * @date:2022/8/7 11:15
 * @author: qyl
 */
public class PhantomRef {
    public static PhantomRef obj;
    static ReferenceQueue<PhantomRef> queue = null;

    public static class CheckRefQueue extends Thread {
        @Override
        public void run() {
            while (true) {
                if (queue != null) {
                    PhantomReference<PhantomRef> objt = null;
                    try {
                        objt = (PhantomReference<PhantomRef>) queue.remove();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (objt != null){
                        System.out.println("追踪垃圾回收过程 虚引用对象已经被回收");
                    }
                }
            }
        }
    }

    @Override
    public void finalize() throws Throwable {
        super.finalize();
        System.out.println("调用当前finalize方法");
        obj = this;
    }

    public static void main(String[] args) {
        Thread t = new CheckRefQueue();
        t.setDaemon(true);
        t.start();



        queue = new ReferenceQueue<>();
        obj = new PhantomRef();
        PhantomReference<PhantomRef> ref = new PhantomReference<>(obj, queue);
        try{
            // 获取虚引用对象 null
            System.out.println(ref.get());

            obj = null;

            System.gc();
            Thread.sleep(3000);
            System.out.println("首次gc");
            if(obj == null){
                System.out.println("obj 为null");
            }else{
                System.out.println("obj 非null");
            }

            obj = null;
            System.gc();
            Thread.sleep(3000);
            System.out.println("第二次Gc");
            if(obj == null){
                System.out.println("obj 为null");
            }else{
                System.out.println("obj 非null");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
