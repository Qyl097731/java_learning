# ACM

## DP

### 数位DP

```java
public class NumricDP {
    static long m, n;
    static long[][][] dp = new long[20][10][2];
    static int[] digit = new int[20];

    public static void main(String[] args) {
        for (int i = 0; i < dp.length; i++) {
            for (int j = 0; j < dp[j].length; j++) {
                Arrays.fill(dp[i][j], -1);
            }
        }
        Scanner scanner = new Scanner(System.in);
        m = scanner.nextLong();
        n = scanner.nextLong();
        long cntm = solve(m);
        long l = 1,r = INF , mid;
        while (l < r) {
            mid = (l + r) >> 1;
            long cntmid = solve(mid);
            if (cntmid - cntm >= n) {
                r = mid;
            } else {
                l = mid + 1;
            }
        }
        System.out.println(r);
    }

    static long solve(long num) {
        int cnt = 0;
        while (num > 0) {
            digit[cnt++] = (int) (num % 10);
            num /= 10;
        }
        return dfs(cnt - 1, 0, 0, true);
    }

    private static long dfs(int pos, int mod, int hasSeven, boolean limit) {
        long sum = 0;
        // 递归边界进行返回 满足条件就是1 否则就是0
        if (pos == -1) {
            return (hasSeven == 1 || mod % 7 == 0) ? 1L : 0L;
        }
        // 查看是不是上届 如果不是上届并且已经记忆化过 直接返回
        if (!limit && dp[pos][mod][hasSeven] != -1) {
            return dp[pos][mod][hasSeven];
        }
        // 根据是不是边界值决定 遍历的边界值
        int up = limit ? digit[pos] : 9;

        // 递归记忆化
        for (int i = 0; i <= up; i++) {
            sum += dfs(pos - 1, (mod * 10 + i) % 7, (i == 7 || hasSeven == 1) ? 1 : 0, limit && i == up);
        }
        // 如果不是边界 才需要进行记忆化 否则就直接返回就好了
        if (!limit) {
            dp[pos][mod][hasSeven] = sum;
        }
        return sum;
    }
}
```



### 状态DP



## 数学

### 康托展开

求解一个排列在全排列中的位序。

> 在（1，2，3，4，5）5个数的排列组合中，计算 34152的康托展开值。
>
> - 首位是3，则小于3的数有两个，为1和2，a[5]=2，则首位小于3的所有排列组合为 a[5]*(5-1)!
> - 第二位是4，则小于4的数有两个，为1和2，注意这里3并不能算，因为3已经在第一位，所以其实计算的是在第二位之后小于4的个数。因此a[4]=2
> - 第三位是1，则在其之后小于1的数有0个，所以a[3]=0
> - 第四位是5，则在其之后小于5的数有1个，为2，所以a[2]=1
> - 最后一位就不用计算啦，因为在它之后已经没有数了，所以a[1]固定为0
> - 根据公式：X = 2 * 4! + 2 * 3! + 0 * 2! + 1 * 1! + 0 * 0! = 2 * 24 + 2 * 6 + 1 = 61，所以比 **34152 小的组合有61个**，即34152是排第62。

```java
    int[] fac = {1, 1, 2, 6, 24, 120, 720, 5040, 40320, 362880};	// 阶乘
    int cantor(int[] a, int n)
    {
        int x = 0;
        for (int i = 0; i < n; ++i) {
            int smaller = 0;  // 在当前位之后小于其的个数
            for (int j = i + 1; j < n; ++j) {
                if (a[j] < a[i]) {
                    smaller++;
                }
            }
            x += fac[n - i - 1] * smaller; // 康托展开累加
        }
        return x;  // 康托展开值
    }

```



### 逆康托展开

求指定位序在全排列中对应地排列。

```java
class Solution {
    int[] fac;
    public String getPermutation(int n, int k) {
        // 减一 表示 比所求排列小的排列数目总和
        k--;
        ArrayList<Integer> list = new ArrayList<>();
        fac = new int[n+1];
        fac[0] = 1;
        for (int i = 1; i <= n; i++) {
            fac[i] = fac[i-1] * i;
            list.add(i);
        }

        StringBuilder res = new StringBuilder();
        for (int i = n; i >= 1; i--) {
            int r = k % fac[i-1];
            int t = k / fac[i-1];
            k = r;
            res.append(list.get(t));
            list.remove(t);
        }
        return res.toString();
    }
}
```

