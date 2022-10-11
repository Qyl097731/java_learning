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

### 悬线法

针对求给定矩阵中满足某条件的极大矩阵，比如“面积最大的长方形、正方形”“周长最长的矩形等等”。

> **悬线法的基本思路：**维护三个二维数组，Left,Right,Up数组。
>
> -   Left数组存储从map[i][j]这个点出发，满足条件能到达的最左边地方。
>
> -   Right数组存储从map[i][j]这个点出发，满足条件能到达的最右边地方。
>
> -   Up数组比较耿直，直接存储从这点以上满足条件的能到达的最大长度。
> -   Left 、 Right 数组都是单调栈的思想体现，如Left到达的最左边地方就是单调栈中找的最左边比他小的数

```java
/**
 * @description
 * @date:2022/10/10 18:17
 * @author: qyl
 */
public class SuspensionDP {
    static int n, m;
    static int[][] map, l, r, up;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        n = scanner.nextInt();
        m = scanner.nextInt();
        map = new int[2010][2010];
        l = new int[2010][2010];
        r = new int[2010][2010];
        up = new int[2010][2010];
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                map[i][j] = Integer.parseInt(scanner.next());
                l[i][j] = r[i][j] = j;
                up[i][j] = 1;
            }
        }

        for (int i = 1; i <= n; i++) {
            for (int j = 2; j <= m; j++) {
                if (map[i][j] != map[i][j - 1]) {
                    l[i][j] = l[i][j - 1];
                }
            }
        }
        for (int i = 1; i <= n; i++) {
            for (int j = m - 1; j >= 1; j--) {
                if (map[i][j + 1] != map[i][j]) {
                    r[i][j] = r[i][j + 1];
                }
            }
        }
        int square = 1;
        int seq = 1;
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                if (i != 1 && map[i][j] != map[i - 1][j]) {
                    up[i][j] = up[i - 1][j] + 1;
                    l[i][j] = Math.max(l[i][j], l[i - 1][j]);
                    r[i][j] = Math.min(r[i][j], r[i - 1][j]);
                }
                seq = Math.max(seq, up[i][j] * (r[i][j] - l[i][j] + 1));
                square = Math.max(square, Math.min(up[i][j], r[i][j] - l[i][j] + 1));
            }
        }
        System.out.println(square * square);
        System.out.println(seq);
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

## 数据结构

### LCA

> 倍增的思想 来解决查找树中两个节点的最近公共祖先节点
>
> 时间复杂度：预处理O（nlogn） 查询O(logn) 
>
> 如果暴力一个节点一个节点想上找必然太慢，所以想到了倍增的思想进行处理。
>
> - 首先让两个节点较深的节点跳到和另一个节点同样的深度，通过二进制&的方式来做差，加速上跳；
> - 然后依旧采用二进制&的方式来做差，让两个节点同时往上跳，找寻各自到父亲路径前的最后一个节点A，B，最后在返回的是A与B再次往上跳一次的点，即公共父亲节点。
> - fa存储当前i节点网上跳了$2^i$之后到达的节点

```c++
#include<bits/stdc++.h>
using namespace std;
const int N = 1e5+5;

int tot,n,q;

int x,y,a,b,k;

struct Edge {
    int v,nex;
} edge[N*2];

int head[N],dep[N],fa[N][21];

void addedge(int u,int v) {
    edge[++tot].v = v;
    edge[tot].nex = head[u];
    head[u] = tot;
}

void dfs(int u,int f) {
    dep[u] = dep[f]+1;
    fa[u][0] = f;
    for(int i = 1; i <= 20 ; i++)
        fa[u][i] = fa[fa[u][i-1]][i-1];
    for(int i = head[u]; ~i ; i = edge[i].nex) {
        int v = edge[i].v;
        if(v!=f)
            dfs(v,u);
    }
}

int lca(int a,int b) {
    if(dep[a] < dep[b])
        swap(a,b);
    int df = dep[a] - dep[b];
    for(int i = 0 ; i < 21 ; i ++)
        if(df&(1<<i))
            a = fa[a][i];
    if(a == b)
        return a;
    for(int i = 20; i >= 0 ; i --)
        if(fa[a][i]!=fa[b][i]) {
            a = fa[a][i];
            b = fa[b][i];
        }
    return fa[a][0];
}

int dist(int a,int b) {
    return dep[a] + dep[b] - 2*dep[lca(a,b)];
}

int main() {
    std::ios::sync_with_stdio(false);
    memset(head,-1,sizeof(head));
    memset(fa,0,sizeof(fa));
    cin>>n;
    for(int i = 1; i <= n-1 ; i ++) {
        int u,v;
        cin>>u>>v;
        addedge(u,v);
        addedge(v,u);
    }
    dfs(1,0);
    cin>>q;
    while(q--) {
        cin>>x>>y>>a>>b>>k;

        int res = dist(a,b);
        if(res <= k && (k-res)%2 == 0) {
            cout<<"YES\n";
            continue;
        }

        res = dist(a,x) + dist(y,b) + 1;
        if(res <= k && (k-res)%2 == 0) {
            cout<<"YES\n";
            continue;
        }

        res = dist(a,y) + dist(x,b) + 1;
        if(res <= k && (k-res)%2 == 0) {
            cout<<"YES\n";
            continue;
        }

        cout<<"NO\n";
    }

}



```

