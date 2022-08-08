package chapter06;

import java.util.ArrayList;
import java.util.List;

/**
 * projectName:  jvm
 * packageName: chapter06
 * date: 2022-07-24 17:09
 * author 邱依良
 */
public class GCTest {
    public static void main(String[] args) {
        int i = 0 ;
        try{
            List<String> list = new ArrayList<>();
            String a = "njxzc";
            while (true) {
                list.add(a);
                a = a + a;
                i++;
            }
        }catch (Throwable t){
            t.printStackTrace();
            System.out.println("遍历次数 : " + i);
        }
    }
}
