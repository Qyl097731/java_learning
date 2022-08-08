package chapter06;

import java.util.ArrayList;
import java.util.Random;

/**
 * projectName:  jvm
 * packageName: chapter06
 * date: 2022-07-24 15:35
 * author 邱依良
 */
public class OOMTest {
    public static void main(String[] args) {
    ArrayList<Picture> list = new ArrayList<>();
        while (true) {
            try {
                Thread.sleep(20);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
            list.add(new Picture(new Random().nextInt(1024*1024)));
        }
    }
}
class Picture{
    private byte[] pixels;

    public Picture(int length){
        this.pixels = new byte[length];
    }
}
