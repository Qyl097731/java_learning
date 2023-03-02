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
                appendix.put (goodsArray[i], new ArrayList<> ());
            }
        }
        for (int i = 0; i < m; i++) {
            Goods goods = goodsArray[i];
            if (goods.q > 0) {
                appendix.computeIfPresent (goodsArray[goods.q - 1],
                        (k, v) -> {
                            v.add (goods);
                            return v;
                        });
            }
        }
        int[] dp = new int[N + 1];
        for (Map.Entry<Goods, List<Goods>> entrySet : appendix.entrySet ()) {
            Goods mainGoods = entrySet.getKey ();
            for (int i = N; i >= mainGoods.v; i--) {
                int totalCost = mainGoods.v;
                int totalWeight = mainGoods.w;
                dp[i] = Math.max (dp[i - mainGoods.v] + mainGoods.w, dp[i]);
                for (Goods sub : entrySet.getValue ()) {
                    if (i - mainGoods.v - sub.v >= 0) {
                        dp[i] = Math.max (dp[i - mainGoods.v - sub.v] + sub.w + mainGoods.w, dp[i]);
                    }
                    totalCost += sub.v;
                    totalWeight += sub.w;
                }
                if (i - totalCost >= 0) {
                    dp[i] = Math.max (dp[i - totalCost] + totalWeight, dp[i]);
                }
            }
        }
        System.out.println (dp[N]);
    }
}

class Goods {
    int v;
    int p;
    int q;
    int w;

    public Goods(int v, int p, int q) {
        this.v = v;
        this.p = p;
        this.q = q;
        this.w = p * v;
    }

    @Override
    public String toString() {
        return "Goods{" +
                "v=" + v +
                ", p=" + p +
                ", q=" + q +
                ", w=" + w +
                '}';
    }
}
