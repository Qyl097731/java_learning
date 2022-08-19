package chapter16;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author qyl
 * @program JprofilerTest1.java
 * @Description Jprofile测试案例1
 * @createTime 2022-08-19 18:21
 */
public class JprofilerTest1 {

    public static void main(String[] args) {
        while (true){
            List list =new ArrayList<>();
            for (int i = 0; i < 500; i++) {
                TestData testData = new TestData();
                list.add(testData);
            }
            try {
                TimeUnit.MILLISECONDS.sleep(500);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }
}

@Data
class TestData{
    private int size = 10;
    private byte[] buffer = new byte[1024 * 1024];
    private String info = "hello ";
}