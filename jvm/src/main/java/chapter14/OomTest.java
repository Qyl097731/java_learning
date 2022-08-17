package chapter14;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @description
 * @date:2022/8/17 23:24
 * @author: qyl
 */
public class OomTest {
    public static void main(String[] args) {
        List<Picture> list = new ArrayList<>();
        while (true) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            list.add(new Picture(new Random().nextInt(100 * 50)));
        }
    }
}
class Picture{
    private byte[] pixels;

    public Picture(int length){
        this.pixels = new byte[length];
    }

}
