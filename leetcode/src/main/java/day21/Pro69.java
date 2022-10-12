//package day21;
//
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Test;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//
///**
// * @description
// * @date:2022/10/12 0:40
// * @author: qyl
// */
//class Solution69 {
//    @Test
//    public void test1(){
//        Assertions.assertEquals(Leetcode(new String[]{"hello", "leetcode"}),0);
//    }
//
//    String t = "helloleetcode";
//    int[] nums = new int[26];
//    int[] target = new int[26];
//    int[] used ;
//    int res = Integer.MAX_VALUE;
//
//    public int Leetcode(String[] words) {
//        int lenn = words.length;
//        int nboundry = (1 << lenn);
//        used = new int[lenn];
//
//        for (String word : words) {
//            char[] chars = word.toCharArray();
//            for (char c : chars) {
//                nums[c - 'a']++;
//            }
//        }
//        char[] chars = t.toCharArray();
//        for (char c : chars) {
//            target[c - 'a']++;
//        }
//
//        for (int i = 0; i < 26; i++) {
//            if (nums[i] < target[i]) {
//                return 0;
//            }
//            if (i!=0){
//                nums[i] += nums[i-1];
//            }
//        }
//
//        int[] temp = new int[26];
//        for (int i = 0; i < nboundry; i++) {
//            for (int j = 0; (1 << j) <= i; j++) {
//                used[lenn - j - 1] = (1<<j) & i;
//            }
//            Arrays.fill(temp,0);
//            dfs(words,new ArrayList<>(),temp,target,lenn,0,0);
//        }
//        return res;
//    }
//
//    void dfs(String[] words,List<List<Integer>> dels,int[] temp,int[] tar,int len,int now,int ddlen){
//        if (now == len){
//            for (int i = 0 ; i < 26;i++){
//                if (tar[i] != temp[i]){
//                    return;
//                }
//            }
//            int slen ,dlen,val = 0;
//            for (int i = 0; i < dels.size(); i++) {
//                slen = words[i].length(); List<Integer> des = dels.get(i); dlen = des.size();
//                for (int j = 0; j < dlen; j++) {
//                    val += (des.get(j) - j)*(slen-des.get(j));
//                }
//            }
//            res = Math.min(val,res);
//        }
//
//        for (int i = now; i < len; i++) {
//            if (used[i] == 1){
//                String s = words[i];
//                int slen = s.length(),wboundry = (1<<slen);
//                for (int j = 0; j < wboundry; j++) {
//                    List<Integer> choose = new ArrayList<>();
//                    int[] ttemp = new int[26];
//                    for (int k = 0; k < 26; k++) {
//                        ttemp[k] = temp[k];
//                    }
//                    for (int k = 0; (1<<k) <= j ; k++) {
//                        if ((1<<k & j) == 1){
//                            choose.add(slen-1-k);
//                            ttemp[s.charAt(slen-1-k)-'a']++;
//                        }
//                    }
//
//                    if (check(ttemp,tar,i)) {
//                        dels.add(choose);
//                        dfs(words, new ArrayList<>(dels), ttemp, tar, len, i + 1, ddlen + 1);
//                        dels.remove(ddlen);
//                    }
//                }
//            }
//        }
//    }
//
//    private boolean check(int[] ttemp, int[] tar, int i) {
//        for (int j = 0; j < ; j++) {
//
//        }
//    }
//
//}
