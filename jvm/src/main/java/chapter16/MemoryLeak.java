package chapter16;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**
 * @description
 * @date:2022/8/20 14:46
 * @author: qyl
 */
public class MemoryLeak {
    public static void main(String[] args) throws InterruptedException {
        while (true){
            ArrayList beans = new ArrayList();
            for (int i = 0; i < 500; i++) {
                Bean bean = new Bean()  ;
                bean.list.add(new byte[1024 * 10]);
                beans.add(bean);
            }
            TimeUnit.MILLISECONDS.sleep(500);
        }
    }
}
class Bean{
    int size = 10;
    String info = "hello";
    static ArrayList list = new ArrayList();
}
