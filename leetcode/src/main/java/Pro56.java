package leetcode;

import java.util.*;

/**
 * projectName:  fushi
 * packageName: leetcode
 * date: 2022-03-28 22:05
 * copyright(c) 2020 南晓18卓工 邱依良
 *
 * @author 邱依良
 */
public class Pro56 {
    public static void main(String[] args) {
        new Solution56().merge(new int[][]{{1,3},{2,6},{8,10},{15,18}});

    }
}
class Solution56 {
    public int[][] merge(int[][] intervals) {
        int len = intervals.length;
        List<int[]>list = new ArrayList<>();
        Arrays.sort(intervals, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                return o1[0] - o2[0];
            }
        });

        int[] scd;
        int[] fst;
        for(int i = 0 ; i < len;i++){
            if(list.isEmpty()){
                list.add(intervals[i]);
            }else{
                fst = list.get(list.size()-1);
                scd = intervals[i];
                if(fst[1] >= scd[0]){
                    fst[1] = Math.max(scd[1],fst[1]);
                }else {
                    list.add(scd);
                }
            }
        }
        return list.toArray(new int[list.size()][2]);

    }
}
