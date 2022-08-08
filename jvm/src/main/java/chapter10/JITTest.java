package chapter10;

import java.util.ArrayList;

/**
 * projectName:  jvm
 * packageName: chapter10 @date: 2022-07-30 23:58
 *
 * @author 邱依良
 */
public class JITTest {
    public static void main(String[] args) {
        ArrayList<String>list = new ArrayList<>();

        for(int i = 0 ; i < 1000;i++){
            list.add("让天下没有难学的技术");

            try{
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
