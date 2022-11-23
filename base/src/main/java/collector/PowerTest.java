package collector;

/**
 * @description map容量计算
 * @date:2022/11/22 22:13
 * @author: qyl
 */
public class PowerTest {
    public static void main(String[] args) {
        int n = 7;
        n = 6;
        n |= n>>>1;
        n |= n>>>2;
        n |= n>>>4;
        n |= n>>>8;
        n |= n>>>16;
    }
}
