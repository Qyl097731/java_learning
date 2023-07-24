package trails;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author qiuyiliang
 */
public class OutOfMemoryTest {

    public static void main(String[] args) {
        new Thread(() -> {
            List<byte[]> list = new ArrayList<>();
            while (true) {
                list.add(new byte[1024 * 1024 * 50]);
                System.out.println(Thread.currentThread().getName());
                try {
                    Thread.sleep(1_000);
                } catch (Exception e) {
                }
            }
        }, "dead thread").start();

        new Thread(() -> {
            while (true) {
                System.out.println(Thread.currentThread().getName());
                try {
                    Thread.sleep(1_000);
                } catch (Exception e) {
                }
            }
        }, "alive thread").start();
    }
}
