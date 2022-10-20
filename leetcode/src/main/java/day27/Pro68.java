package day27;

import java.util.ArrayList;
import java.util.List;

/**
 * @description
 * @date:2022/10/19 22:43
 * @author: qyl
 */
public class Pro68 {
    public static void main(String[] args) {
        System.out.println(new Solution68().fullJustify(new String[]{"This", "is", "an", "example", "of", "text",
                "justification."
        }, 16));
    }
}

class Solution68 {
    public List<String> fullJustify(String[] words, int maxWidth) {
        int n = words.length, start = 0;
        int[] row = new int[2];
        List<String> res = new ArrayList<>();
        StringBuilder sb, rowRes;
        while (start < n) {
            row = findLastWord(start, words, n, maxWidth);
            rowRes = new StringBuilder(words[start]);
            if (row[0] == n-1) {
                for (int i = start + 1; i < n; i++) {
                    rowRes.append(" ").append(words[i]);
                }
                while (rowRes.length() < maxWidth) {
                    rowRes.append(" ");
                }
                res.add(rowRes.toString());
                break;
            }
            int blanks = row[0] == start ?
                    maxWidth - row[1] :
                    (maxWidth - row[1]) / (row[0] - start);
            sb = new StringBuilder();
            while (blanks-- > 0) {
                sb.append(" ");
            }
            if (row[0] == start){
                rowRes.append(sb);
            }else {
                int retains = (maxWidth - row[1]) % (row[0] - start);
                for (int i = start + 1; i <= row[0]; i++) {
                    rowRes.append(sb);
                    if (retains > 0){
                        retains--;
                        rowRes.append(" ");
                    }
                    rowRes.append(words[i]);
                }
            }
            res.add(rowRes.toString());
            start = row[0] + 1;
        }
        return res;
    }

    private int[] findLastWord(int start, String[] words, int n, int maxWidth) {
        int end = start + 1, cost = words[start].length() + 1,realCost = words[start].length();
        while (end < n && cost + words[end].length() <= maxWidth) {
            cost = cost + 1 + words[end].length();
            realCost += words[end].length();
            end++;
        }
        return new int[]{end - 1, realCost};
    }
}
