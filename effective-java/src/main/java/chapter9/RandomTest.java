package chapter9;

/**
 * @description
 * @date:2022/9/6 20:18
 * @author: qyl
 */
public class RandomTest {
    static int random(int n){
        return Math.abs(Integer.MIN_VALUE) % 4;
    }
    public static void main(String[] args) {
        System.out.println(random(10));
    }
}
