package daily;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description
 * @date 2023/3/2 21:51
 * @author: qyl
 */
public class HJ16 {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader (new InputStreamReader (System.in));
        String[] string = reader.readLine ().split (" ");

        int N = Integer.parseInt (string[0]), m = Integer.parseInt (string[1]);
        Goods[] goodsArray = new Goods[m];
        Map<Goods, List<Goods>> appendix = new HashMap<> ();
        for (int i = 0; i < m; i++) {
            string = reader.readLine ().split (" ");
            goodsArray[i] = new Goods (Integer.parseInt (string[0]), Integer.parseInt (string[1]), Integer.parseInt (string[2]));
            if (goodsArray[i].q == 0) {
                appendix.put (goodsArray[i], new ArrayList<Goods> ());
            }
        }
        for (int i = 0; i < m; i++) {
            if (goodsArray[i].q > 0) {
                List<Goods> goods = appendix.get (goodsArray[goodsArray[i].q - 1]);
                goods.add (goodsArray[i]);
                appendix.put (goodsArray[goodsArray[i].q - 1], goods);
            }
        }

        int[] dp = new int[N + 1];
        for (Map.Entry<Goods, List<Goods>> entrySet : appendix.entrySet ()) {
            Goods mainGoods = entrySet.getKey ();
            int zeroCost = mainGoods.v, totalCost = zeroCost, totalWeight = zeroCost * mainGoods.p;
            for (int i = N; i >= zeroCost; i--) {
                dp[i] = Math.max (dp[i - zeroCost] + zeroCost * mainGoods.p, dp[i]);
                for (Goods sub : entrySet.getValue ()) {
                    dp[i] = Math.max (dp[Math.max (0, i - zeroCost - sub.v)] + sub.v * sub.p + zeroCost * mainGoods.p, dp[i]);
                    totalCost += sub.v;
                    totalWeight += sub.v * sub.p;
                }
                dp[i] = Math.max (dp[Math.max (0, i - totalCost)] + totalWeight, dp[i]);
                System.out.println (dp[i]);
            }
        }
        System.out.println (dp[N]);
    }
}

class Goods {
    int v;
    int p;
    int q;

    public Goods(int v, int p, int q) {
        this.v = v;
        this.p = p;
        this.q = q;
    }
}
