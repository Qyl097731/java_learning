package day30;

/**
 * @description
 * @date:2022/10/22 14:15
 * @author: qyl
 */
public class Pro1035 {
    public static void main(String[] args) {
        System.out.println(new Solution1035().maxUncrossedLines(new int[]{3}, new int[]{3, 3, 2}));
    }
}
class Solution1035 {
    public int maxUncrossedLines(int[] nums1, int[] nums2) {
        int n = nums1.length, m = nums2.length;
        int[][] dp = new int[n+1][m+1];

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m ; j++) {
                if (nums1[i-1] == nums2[j-1]) {
                    dp[i][j] = Math.max(dp[i-1][j-1]+1,dp[i][j]);
                }else{
                    dp[i][j] = Math.max(dp[i][j-1],dp[i-1][j]);
                }
            }
        }
        return dp[n][m];
    }
}
