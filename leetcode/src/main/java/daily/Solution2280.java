package daily;

import java.util.Arrays;
import java.util.Comparator;

/**
 * @description
 * @date:2022/12/6 14:08
 * @author: qyl
 */
public class Solution2280 {
    public int minimumLines(int[][] stockPrices) {
        Arrays.sort (stockPrices, new Comparator<int[]> ( ) {
            @Override
            public int compare(int[] o1, int[] o2) {
                return Integer.compare (o1[0], o2[0]);
            }
        });
        int i = 0, j, n = stockPrices.length;
        int res = 0;
        while (i + 1 < n) {
            j = i + 1;
            int dify = stockPrices[j][1] - stockPrices[i][1];
            int difx = stockPrices[j][0] - stockPrices[i][0];
            while (j < n) {
                int tempDify = stockPrices[j][1] - stockPrices[i][1];
                int tempDifx = stockPrices[j][0] - stockPrices[i][0];
                if (difx * tempDify != dify * tempDifx) {
                    break;
                }
                j++;
            }
            i = j - 1;
            res++;
        }
        return res;
    }
}
