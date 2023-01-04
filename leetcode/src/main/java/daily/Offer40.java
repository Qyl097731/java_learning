package daily;

import java.util.Arrays;

/**
 * @description
 * @date:2023/1/4 17:40
 * @author: qyl
 */
public class Offer40 {
    public int[] getLeastNumbers(int[] arr, int k) {
        Arrays.sort (arr);
        return Arrays.copyOfRange (arr, 0, k);
    }
}
