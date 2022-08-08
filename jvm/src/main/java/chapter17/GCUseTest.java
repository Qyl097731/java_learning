package chapter17;

import java.util.ArrayList;

/**
 * @description 查看垃圾回收器
 * @date:2022/8/7 13:55
 * @author: qyl
 */
public class GCUseTest {
    public static void main(String[] args) {
        ArrayList<byte[]> list = new ArrayList<>();

        while (true){
            byte[] arr = new byte[100];
            list.add(arr);
            try {
                Thread.sleep(10);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
