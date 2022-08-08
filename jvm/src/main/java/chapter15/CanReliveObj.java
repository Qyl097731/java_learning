package chapter15;

import java.util.concurrent.TimeUnit;

/**
 * @description
 * @date:2022/8/6 22:25
 * @author: qyl
 */
public class CanReliveObj {
    public static CanReliveObj obj; //类变量 属于GC Root

    @Override
    protected void finalize() throws Throwable{
        super.finalize();
        System.out.println("调用当前类的finalize（）方法");
        obj = this;
    }

    public static void main(String[] args) {
        try{
            obj = new CanReliveObj();
            obj = null;
            System.gc();
            System.out.println("首次gc");
            TimeUnit.SECONDS.sleep(2);
            if(obj == null){
                System.out.println("obj is dead");
            }else{
                System.out.println("obj is still alvie");
            }
            System.out.println("第二次gc");
            obj = null;
            if(obj == null){
                System.out.println("obj is dead");
            }else{
                System.out.println("obj is still alvie");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
