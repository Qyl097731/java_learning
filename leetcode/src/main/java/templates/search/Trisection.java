package templates.search;

/**
 * @description
 * @date 2023/3/25 21:48
 * @author: qyl
 */
public class Trisection {
    double find(double l, double r) {
        while (l + 1e-18 < r) {
            double mid = l + (r - l) / 2;
            double midd = mid + (r - mid) / 2;
            if (solve (mid) <= solve (midd)) {
                r = midd;
            } else {
                l = mid;
            }
        }
        return r;
    }

    double solve(double num) {
        return 0d;
    }
}
