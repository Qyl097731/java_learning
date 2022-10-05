package day13;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @description
 * @date:2022/10/2 17:30
 * @author: qyl
 */
public class Pro51 {
    public static void main(String[] args) {
        List<List<String>> ress = new Solution51().solveNQueens(1);
        System.out.println(ress);
    }
}

class Solution51 {
    boolean[] col;
    boolean[][] diagonal;
    char[][] mp;
    List<List<String>> res = new ArrayList<>();

    public List<List<String>> solveNQueens(int n) {
        col = new boolean[n];
        diagonal = new boolean[n*2][2];
        mp = new char[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                mp[i][j] = '.';
            }
        }
        dfs(0, n, mp);
        return res;
    }

    private void dfs(int now, int n, char[][] mp) {
        if (now == n) {
            List<String> strings = new ArrayList<>();
            for (char[] cs : mp) {
                StringBuilder sb = new StringBuilder();
                for (char c : cs){
                    sb.append(c);
                }
                strings.add(sb.toString());
            }
            res.add(strings);
            return;
        }

        for (int i = 0; i < n; i++) {
            if (!col[i] && !diagonal[i+now][0] && !diagonal[i-now+n][1]){
                col[i] = true;
                diagonal[i+now][0] = true;
                diagonal[i-now+n][1] = true;
                mp[now][i] = 'Q';
                dfs(now+1,n,mp);
                col[i] = false;
                diagonal[i+now][0]= false;
                diagonal[i-now+n][1] = false;
                mp[now][i] = '.';
            }
        }
    }


}
