package day25;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.*;

/**
 * @description
 * @date:2022/10/17 0:52
 * @author: qyl
 */
public class Pro886 {
    public static void main(String[] args) {
        System.out.println(new Solution886().possibleBipartition(4, new int[][]{{1, 2}, {1, 3}, {2, 4}}));
    }
}

class Solution886 {

    public boolean possibleBipartition(int n, int[][] dislikes) {
//        return solve1(n,dislikes);
        return solve2(n, dislikes);
    }

    private boolean solve2(int n, int[][] dislikes) {
        int[] height = new int[n + 1], f = new int[n + 1];
        List<Integer>[] g = new List[n + 1];

        for (int i = 0; i <= n; i++) {
            height[i] = 1;
            f[i] = i;
            g[i] = new ArrayList<>();
        }

        for (int[] dislike : dislikes) {
            int u = dislike[0], v = dislike[1];
            g[u].add(v);
            g[v].add(u);
        }

        for (int i = 1; i <= n; i++) {
            for (int j = 0; j < g[i].size(); j++) {
                merge(g[i].get(0), g[i].get(j), f, height);
                if (find(i,f) == find(g[i].get(j), f)) {
                    return false;
                }
            }
        }
        return true;

    }

    void merge(int x, int y, int[] f, int[] height) {
        x = find(x, f);
        y = find(y, f);
        if (x != y) {
            if (height[x] < height[y]) {
                f[x] = y;
            } else {
                f[y] = x;
                height[x] += (height[x] == height[y] ? 1 : 0);
            }
        }
    }

    private int find(int x, int[] f) {
        return f[x] = f[x] == x ? x : find(f[x], f);
    }


    private boolean solve1(int n, int[][] dislikes) {
        int len = dislikes.length;
        int[] color = new int[n + 1];
        List<Integer>[] g = new List[n + 1];
        for (int i = 1; i <= n; i++) {
            g[i] = new ArrayList<>();
        }
        for (int i = 0; i < len; i++) {
            int u = dislikes[i][0];
            int v = dislikes[i][1];
            g[u].add(v);
            g[v].add(u);
        }
        for (int i = 1; i <= n; i++) {
            if (color[i] == 0 && !dfs(i, g, color, 0)) {
                return false;
            }
        }
        return true;
    }

    private boolean dfs(int u, List<Integer>[] g, int[] color, int c) {
        color[u] = c;
        for (Integer e : g[u]) {
            if (color[e] != 0 && color[e] == c) {
                return false;
            }
            if (color[e] == 0 && !dfs(e, g, color, c ^ 1)) {
                return false;
            }
        }
        return true;
    }
}
