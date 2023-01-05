package daily;

/**
 * @description
 * @date:2023/1/5 18:55
 * @author: qyl
 */
public class Offer56II {
    public int singleNumber(int[] nums) {
        int res = 0;
        int[] cnt = new int[32];
        for (int i = 0; i < 31; i++) {
            for (int num : nums) {
                cnt[i] += ((num >> i) & 1);
            }
        }
        for (int i = 0; i < 32; i++) {
            if (cnt[i] % 3 != 0) {
                res |= (1 << i);
            }
        }
        return res;
    }
}
