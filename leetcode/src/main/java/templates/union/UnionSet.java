package templates.union;

import java.util.Scanner;

/**
 * @description 并查集
 * @date:2022/10/17 0:39
 * @author: qyl
 */
public class UnionSet {
    private static final int maxn = (int)1e5+5;
    static int n,k,x,y;
    static int[] root = new int[maxn];
    static int[] height = new int[maxn];
    static void init(){ for (int i = 0; i < maxn; i++) { root[i] = i;height[i] =1; } }
    static int find(int x){return root[x] = x==root[x] ? root[x] :find(root[x]);}
    static void merge(int x,int y){
        x = find(x);y = find(y);
        if (x != y) {
            if (height[x] < height[y]) root[x] = y;
            else {
                root[y] = x;
                height[x] += (height[x] == height[y]?1:0);
            }
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        n = sc.nextInt();k = sc.nextInt();
        while(k-->0){
            x = sc.nextInt(); y = sc.nextInt();
            merge(x,y);
        }
        int q = sc.nextInt();
        for (int i = 0; i < q; i++) {
            x = sc.nextInt(); y = sc.nextInt();
            if (find(x) == find(y)){
                System.out.println("Yes");
            }else{
                System.out.println("No");
            }
        }
    }
}
