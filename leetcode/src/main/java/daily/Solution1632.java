package daily;

import java.util.*;

/**
 * @description
 * @date:2023/1/25 22:20
 * @author: qyl
 */
public class Solution1632 {
    int[][] matrix;
    UnionFind uf;
    int[][] degree;
    Map<Integer, List<int[]>> adj;

    public int[][] matrixRankTransform(int[][] matrix) {
        int m = matrix.length, n = matrix[0].length;
        this.matrix = matrix;
        this.degree = new int[m][n];
        this.adj = new HashMap<> ();
        /**
         * 建立并查集
         */
        uf = new UnionFind (m, n);
        int[][] res = new int[m][n];
        mergeIndex (m, n);
        mergeIndex (n, m);
        /**
         * 建图
         */
        addEdges (m, n, n);
        addEdges (n, m, n);
        /**
         * 缩点
         */
        Set<Integer> rootSet = new HashSet<> ();
        int[][] ranks = new int[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                int[] rootArr = uf.find (i, j);
                int ri = rootArr[0], rj = rootArr[1];
                rootSet.add (ri * n + rj);
                ranks[ri][rj] = 1;
            }
        }
        Queue<int[]> queue = new ArrayDeque<> ();
        for (int v : rootSet) {
            if (degree[v / n][v % n] == 0) {
                queue.offer (new int[]{v / n, v % n});
            }
        }
        while (!queue.isEmpty ()) {
            int[] arr = queue.poll ();
            int i = arr[0], j = arr[1];
            for (int[] edge : adj.getOrDefault (i * n + j, new ArrayList<> ())) {
                int ui = edge[0], uj = edge[1];
                degree[ui][uj]--;
                if (degree[ui][uj] == 0) {
                    queue.offer (new int[]{ui, uj});
                }
                ranks[ui][uj] = Math.max (ranks[i][j] + 1, ranks[ui][uj]);
            }
        }
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                int[] rootArr = uf.find (i, j);
                res[i][j] = ranks[rootArr[0]][rootArr[1]];
            }
        }
        return res;
    }

    private void addEdges(int m, int n, int col) {
        for (int i = 0; i < m; i++) {
            Map<Integer, int[]> num2Index = new HashMap<> ();
            for (int j = 0; j < n; j++) {
                int num = matrix[i][j];
                num2Index.put (num, new int[]{i, j});
            }
            List<Integer> sortedArray = new ArrayList<> (num2Index.keySet ());
            Collections.sort (sortedArray);
            for (int j = 1; j < sortedArray.size (); j++) {
                int[] pre = num2Index.get (sortedArray.get (j - 1));
                int[] cur = num2Index.get (sortedArray.get (j));
                int i1 = pre[0], j1 = pre[1], i2 = cur[0], j2 = pre[1];
                int[] root1 = uf.find (i1, j1);
                int[] root2 = uf.find (i2, j2);
                int ri1 = root1[0], rj1 = root1[1], ri2 = root2[0], rj2 = root2[1];
                degree[ri2][rj2]++;
                adj.computeIfAbsent (ri1 * col + rj1, k -> new ArrayList<> ()).add (new int[]{ri2, rj2});
            }
        }
    }

    private void mergeIndex(int m, int n) {
        for (int i = 0; i < m; i++) {
            Map<Integer, List<int[]>> num2indexList = new HashMap<> ();
            for (int j = 0; j < n; j++) {
                int num = matrix[i][j];
                num2indexList.computeIfAbsent (num, (k) -> new ArrayList<int[]> ()).add (new int[]{i, j});
            }
            for (List<int[]> indexList : num2indexList.values ()) {
                int[] arr1 = indexList.get (0);
                int i1 = arr1[0], j1 = arr1[1];
                for (int j = 1; j < indexList.size (); j++) {
                    int[] arr2 = indexList.get (j);
                    int i2 = arr2[0], j2 = arr2[1];
                    uf.union (i1, j1, i2, j2);
                }
            }
        }
    }
}

class UnionFind {
    int m, n;
    int[][][] root;
    int[][] size;

    public UnionFind(int m, int n) {
        this.m = m;
        this.n = n;
        this.root = new int[m][n][2];
        this.size = new int[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                root[i][j] = new int[]{i, j};
                size[i][j] = 1;
            }
        }
    }

    public int[] find(int i, int j) {
        int[] rootArr = root[i][j];
        int ri = rootArr[0], rj = rootArr[1];
        if (ri == i && rj == j) {
            return rootArr;
        }
        return find (ri, rj);
    }

    public void union(int i1, int j1, int i2, int j2) {
        int[] rootArr1 = find (i1, j1);
        int[] rootArr2 = find (i2, j2);
        int ri1 = rootArr1[0], rj1 = rootArr1[1];
        int ri2 = rootArr2[0], rj2 = rootArr2[1];
        if (ri1 != ri2 || rj1 != rj2) {
            if (size[ri1][rj1] <= size[ri2][rj2]) {
                root[ri1][rj1][0] = ri2;
                root[ri1][rj1][1] = rj2;
                size[ri2][rj2] += size[ri1][rj1];
            } else {
                root[ri2][rj2][0] = ri1;
                root[ri2][rj2][1] = rj1;
                size[ri1][rj1] += size[ri2][rj2];
            }
        }
    }
}
