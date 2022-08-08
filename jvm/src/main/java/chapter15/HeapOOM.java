package chapter15;

import java.util.ArrayList;
import java.util.List;

/**
 * @description
 * @date:2022/8/6 23:23
 * @author: qyl
 */
public class HeapOOM {
    byte[] buffer = new byte[1 * 1024 * 1024];

    public static void main(String[] args) {
        List<HeapOOM> list = new ArrayList<>();

        int count = 0;
        try{
            while (true){
                list.add(new HeapOOM());
                count++;
            }
        }catch (Throwable throwable){
            System.out.println("count :" + count);
            throwable.printStackTrace();
        }
    }
}
