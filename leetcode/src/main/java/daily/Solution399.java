package daily;

import java.util.*;

/**
 * @description
 * @date:2023/1/19 22:17
 * @author: qyl
 */
public class Solution399 {
    public double[] calcEquation(List<List<String>> equations, double[] values, List<List<String>> queries) {
//        return solve1(equations,values,queries);
        return solve2 (equations, values, queries);
    }

    private double[] solve2(List<List<String>> equations, double[] values, List<List<String>> queries) {
        int n = equations.size (), q = queries.size ();
        double[] res = new double[q];
        UnionFind unionFind = new UnionFind (2 * n);
        // String -> 节点编号
        Map<String, Integer> map = new HashMap<> (2 * n);
        int id = 0;
        for (int i = 0; i < n; i++) {
            List<String> equation = equations.get (i);
            String u = equation.get (0), v = equation.get (1);
            if (!map.containsKey (u)) {
                map.put (u, id);
                id++;
            }
            if (!map.containsKey (v)) {
                map.put (v, id);
                id++;
            }
            unionFind.union (map.get (u), map.get (v), values[i]);
        }

        for (int i = 0; i < q; i++) {
            String u = queries.get (i).get (0), v = queries.get (i).get (1);

            Integer u1 = map.get (u), v1 = map.get (v);
            if (u1 == null || v1 == null) {
                res[i] = -1;
            } else {
                res[i] = unionFind.isConnected (u1, v1);
            }
        }

        return res;
    }

    private double[] solve1(List<List<String>> equations, double[] values, List<List<String>> queries) {
        Set<String> points = new HashSet<> ();
        Map<String, Map<String, Double>> g = new HashMap<> ();
        for (int i = 0; i < equations.size (); i++) {
            List<String> equation = equations.get (i);
            double value = values[i];
            String u = equation.get (0), v = equation.get (1);
            points.add (u);
            points.add (v);
            g.computeIfAbsent (u, (k -> new HashMap<> ())).put (v, value);
            g.computeIfAbsent (v, (k -> new HashMap<> ())).put (u, 1.0 / value);
        }

        for (String k : points) {
            for (String i : points) {
                for (String j : points) {
                    if ((g.containsKey (i) && g.get (i).containsKey (k)) && (g.containsKey (k) && g.get (k).containsKey (j))) {
                        g.computeIfAbsent (i, (v -> new HashMap<> ())).put (j, g.get (i).get (k) * g.get (k).get (j));
                    }
                }
            }
        }

        int q = queries.size ();
        double[] res = new double[q];
        for (int i = 0; i < q; i++) {
            String a = queries.get (i).get (0),
                    b = queries.get (i).get (1);
            if (g.containsKey (a)) {
                Map<String, Double> map = g.get (a);
                if (map.containsKey (b)) {
                    res[i] = map.get (b);
                } else {
                    res[i] = -1;
                }
            } else {
                res[i] = -1;
            }
        }
        return res;
    }

    class UnionFind {
        private int[] root;
        private double[] weight;

        public UnionFind(int n) {
            root = new int[n];
            weight = new double[n];
            for (int i = 0; i < n; i++) {
                root[i] = i;
                weight[i] = 1.0;
            }
        }

        public int find(int x) {
            if (x != root[x]) {
                int origin = root[x];
                // 先回溯 更新父节点的权值
                root[x] = find (root[x]);
                // 当前节点的权值 *= 父节点更新后的权值
                weight[x] *= weight[origin];
            }
            return root[x];
        }

        public void union(int x, int y, double value) {
            int rootX = find (x), rootY = find (y);
            if (rootX == rootY) {
                return;
            }
            root[rootX] = rootY;
            weight[rootX] = weight[y] * value / weight[x];
        }

        public double isConnected(int x, int y) {
            int rootX = find (x), rootY = find (y);
            if (rootX == rootY) {
                return weight[x] / weight[y];
            }
            return -1.0;
        }
    }
}
