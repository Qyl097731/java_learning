package chapter15;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;

/**
 * @author qyl
 * @program HashSetTest.java
 * @Description hash导致内存泄露
 * @createTime 2022-08-19 13:14
 */
public class HashSetTest {
    public static void main(String[] args) {
        HashSet<Point> hs = new HashSet<Point>();
        Point point1 = new Point(1,2);
        Point point2 = new Point(2,3);

        hs.add(point1);
        hs.add(point2);

        System.out.println(hs);

        point1.setX(2);
        hs.remove(point1);
        System.out.println(hs);
    }
}

@Data
@AllArgsConstructor
@NoArgsConstructor
class Point {
    private int x, y;

    @Override
    public int hashCode() {
        return x * 10 + y;
    }

}
