package day17;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @description
 * @date:2022/10/8 0:05
 * @author: qyl
 */
public class Pro870 {
    public static void main(String[] args) {
        System.out.println(Arrays.toString(new Solution870().advantageCount(new int[]{2,7,11,15}, new int[]{1,10,4,
                11})));
    }
}

class Solution870 {
    public int[] advantageCount(int[] nums1, int[] nums2) {
        int len1 = nums1.length, len2 = nums2.length;
        Arrays.sort(nums1);
        List<Integer> list = new ArrayList<>();
        int[] res = new int[len1];
        for (int num : nums1) {
            list.add(num);
        }
        int tot = 0,idx = 0;
        for (int num : nums2) {
            idx = binarySearch(list,num,len1);
            res[tot] = list.get(idx);
            list.remove(idx);
            tot++;
            len1--;
        }
        return res;
    }

    private int binarySearch(List<Integer> list, int num,int len) {
        int l = 0 , r = len-1;
        int mid, lnum;
        while(l < r){
            mid = (l+r)>>1;
            lnum = list.get(mid);
            if (lnum > num ){
                r = mid;
            }else{
                l = mid+1;
            }
        }
        return list.get(r) > num ? r : 0;
    }
}
