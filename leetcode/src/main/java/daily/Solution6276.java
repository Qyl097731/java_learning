package daily;

/**
 * @description
 * @date:2022/12/27 22:04
 * @author: qyl
 */
public class Solution6276 {
    static final int Mod = (int) 1e9 + 7;
    static long[] fac = new long[1000008], inv = new long[10000008];

    static {
        init (fac.length);
    }

    static void init(int n) {
        inv[1] = 1;
        inv[0] = 1;
        fac[0] = 1;
        for (int i = 2; i < n; i++) {
            inv[i] = (Mod - Mod / i) * inv[Mod % i] % Mod;
        }
        for (int i = 1; i < n; i++) {
            fac[i] = fac[i - 1] * i % Mod;
            inv[i] = inv[i] * inv[i - 1] % Mod;
        }
    }

    public int countAnagrams(String s) {
        int n = s.length ();
        long res = 1;
        init (n);
        String[] split = s.split (" ");
        for (String s1 : split) {
            int len = s1.length ();
            int[] nums = new int[26];
            for (int i = 0; i < len; i++) {
                nums[s1.charAt (i) - 'a']++;
            }
            long b = 1;
            for (int num : nums) {
                if (num >= 1) {
                    b = (b * inv[num]) % Mod;
                }
            }
            res = (res * fac[len] % Mod * b) % Mod;
        }
        return (int) res;
    }
}
