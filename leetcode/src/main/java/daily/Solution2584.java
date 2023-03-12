package daily;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @description
 * @date 2023/3/12 0:58
 * @author: qyl
 */
public class Solution2584 {
    static int N = (int) 1e6 + 4;
    static int[] prime = new int[N];
    static boolean[] vis = new boolean[N];

    static {
        Arrays.fill (vis, true);
        vis[1] = vis[0] = false;
        for (int i = 2; i < N; i++) {
            if (vis[i]) {
                prime[++prime[0]] = i;
            }
            for (int j = 1; j <= prime[0] && prime[j] < N / i; j++) {
                vis[prime[j] * i] = false;
                if (i % prime[j] == 0) {
                    break;
                }
            }
        }
    }

    public int findValidSplit(int[] nums) {
        int result = -1;
        int n = nums.length;
        int[] pos = new int[n];
        Map<Integer, Integer> left = new HashMap<> ();
        for (int i = 0; i < n; i++) {
            int num = nums[i];
            for (int j = 1; j <= prime[0] && prime[j] <= num / prime[j]; j++) {
                if (num % prime[j] == 0) {
                    if (left.containsKey (prime[j])) {
                        pos[left.get (prime[j])] = i;
                    } else {
                        left.put (prime[j], i);
                    }
                    while (num % prime[j] == 0) {
                        num /= prime[j];
                    }
                }
            }
            if (num > 1) {
                if (left.containsKey (num)) {
                    pos[left.get (num)] = i;
                } else {
                    left.put (num, i);
                }
            }
        }
        int right = 0;
        for (int i = 0; i < n; i++) {
            if (right < i) {
                return right;
            }
            right = Math.max (right, pos[i]);
        }
        return result;
    }

    @Test
    public void test() {
        findValidSplit (new int[]{3, 15, 5});
    }

}
