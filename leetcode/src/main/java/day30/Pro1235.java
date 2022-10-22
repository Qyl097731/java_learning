package day30;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Comparator;

/**
 * @description
 * @date:2022/10/22 14:59
 * @author: qyl
 */
class Solution1235 {
    public int jobScheduling(int[] startTime, int[] endTime, int[] profit) {
        int n = startTime.length;
        int[] dp = new int[n+1];
        int[][] jobs = new int[n][3];
        for (int i = 0; i < n; i++) {
            jobs[i] = new int[]{startTime[i],endTime[i],profit[i]};
        }
        Arrays.sort(jobs, Comparator.comparingInt(a -> a[1]));
        for (int i = 1; i <= n; i++) {
            int k = binarySearch(i-1,jobs[i-1][0],jobs);
            dp[i] = Math.max(dp[k] + jobs[i-1][2],dp[i-1]);
        }
        return dp[n];
    }

    /**
     * 找出能够做当前任务的那个时间所对应的任务id
     * @param n
     * @param num
     * @return
     */
    int binarySearch(int n,int num,int[][] jobs){
        int l = 0 , r = n , mid;
        while(l < r){
            mid = (l+r)>>1;
            if (jobs[mid][1] <= num){
                l = mid + 1;
            }else{
                r = mid;
            }
        }
        return l;
    }

    @Test
    public void test(){
        System.out.println(jobScheduling(new int[]{1,2,3,4,6}, new int[]{3,5,10,6,9}, new int[]{20,20,100,70,60}));
    }

}
