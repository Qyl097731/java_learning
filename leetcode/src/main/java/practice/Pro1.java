package practice;

import java.util.Scanner;

/**
 * @description
 * @date:2022/10/23 18:02
 * @author: qyl
 */
public class Pro1 {
    static long r;
    static long b, s, c;
    static String recipe;
    static int[] available = new int[3];
    static int[] price = new int[3];

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        recipe = sc.next();
        for (int i = 0; i < 3; i++) {
            available[i] = sc.nextInt();
        }
        for (int i = 0; i < 3; i++) {
            price[i] = sc.nextInt();
        }
        r = sc.nextLong();

        b = recipe.chars().filter(c -> c == 'B').count();
        s = recipe.chars().filter(c -> c == 'S').count();
        c = recipe.chars().filter(c -> c == 'C').count();

        long l = 0, r = 1000000000100L, mid;
        while (l < r) {
            mid = (l + r + 1) / 2;
            if (check(mid)) {
                l = mid;
            } else {
                r = mid - 1;
            }
        }
        System.out.println(l);
    }

    private static boolean check(long mid) {
        long needFromB = mid * b;
        long needFromS = mid * s;
        long needFromC = mid * c;
        long temp = r;
        if (needFromB > available[0]) {
            temp -= (needFromB - available[0]) * price[0];
        }
        if (needFromS > available[1]) {
            temp -= (needFromS - available[1]) * price[1];
        }
        if (needFromC > available[2]) {
            temp -= (needFromC - available[2]) * price[2];
        }

        return temp >= 0;
    }
}
