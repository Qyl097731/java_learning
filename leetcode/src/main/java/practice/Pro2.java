package practice;

import java.io.FileInputStream;
import java.util.Scanner;

/**
 * @description
 * @date:2022/10/23 18:44
 * @author: qyl
 */

import java.util.Scanner;

/**
 * @description
 * @date:2022/10/23 18:44
 * @author: qyl
 */
public class Pro2{
    static long t, n;
    static long m;

    public static void main(String[] args){

        Scanner sc = new Scanner(System.in);
        t = sc.nextLong();
        while (t-- != 0) {
            n = sc.nextLong();m = sc.nextLong();
            long l = -(long)5e15, r = (long)5e15;
            while (l < r) {
                long mid = (l + r) >> 1;
                if (check(mid)) {
                    r = mid;
                } else {
                    l = mid + 1;
                }
            }
            System.out.println(r);
        }
    }

    public static boolean check(long x)
    {
        long cnt = 0;
        for (long i = 1; i <= n; ++i) {
            long l = 0;
            long r = n;
            while (l < r) {
                long mid = (l + r + 1) >> 1;
                if (cal(mid, i) > x) {
                    r = mid - 1;
                } else {
                    l = mid;
                }
            }
            cnt += l;
        }
        return cnt >= m;
    }

    public static long cal(long i, long j) {
        return i * i + 100000L * i + j * j - 100000L * j + i * j;
    }
}
