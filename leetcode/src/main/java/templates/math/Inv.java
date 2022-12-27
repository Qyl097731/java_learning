package templates.math;

/**
 * @description 逆元打表
 * @date:2022/12/27 22:25
 * @author: qyl
 */
public class Inv {
    final int Mod = (int) 1e9 + 7;
    long[] fac = new long[1000008], inv = new long[10000008];

    void init(int n) {
        inv[1] = 1;
        inv[0] = 1;
        fac[0] = 1;

        /**
         * 求解逆元，参看ACM.md
         */
        for (int i = 2; i <= n; i++) {
            inv[i] = (Mod - Mod / i) * inv[Mod % i] % Mod;
        }
        /**
         * fac 阶乘 inv 逆元的阶乘
         */
        for (int i = 1; i <= n; i++) {
            fac[i] = fac[i - 1] * i % Mod;
            inv[i] = inv[i] * inv[i - 1] % Mod;
        }
    }
}
