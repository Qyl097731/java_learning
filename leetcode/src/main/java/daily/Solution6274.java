package daily;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description
 * @date:2022/12/24 22:25
 * @author: qyl
 */
public class Solution6274 {
    public List<Integer> topStudents(String[] positive_feedback, String[] negative_feedback, String[] report, int[] student_id, int k) {
        Map<String, Integer> score = new HashMap<> ( );
        for (String s : positive_feedback) {
            score.put (s, 3);
        }
        for (String s : negative_feedback) {
            score.put (s, -1);
        }
        int n = report.length;
        List<int[]> temp = new ArrayList<> ( );
        for (int i = 0; i < n; i++) {
            String s = report[i];
            String[] split = s.split (" ");
            int sum = 0;
            for (int j = 0; j < split.length; j++) {
                sum += score.getOrDefault (split[j], 0);
            }
            temp.add (new int[]{student_id[i], sum});
        }
        temp.sort ((o1, o2) -> {
            if (o1[1] == o2[1]) return o1[0] - o2[0];
            return o1[2] - o2[1];
        });
        List<Integer> res = new ArrayList<> ( );
        for (int i = 0; i < k; i++) {
            res.add (temp.get (i)[0]);
        }
        return res;
    }
}
