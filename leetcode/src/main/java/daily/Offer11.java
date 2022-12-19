package daily;

/**
 * @description
 * @date:2022/12/19 17:25
 * @author: qyl
 */
public class Offer11 {
    public int minArray(int[] numbers) {
        int res = Integer.MAX_VALUE;
        for (int number : numbers) {
            res = Math.min (res, number);
        }
        return res;
    }
}
