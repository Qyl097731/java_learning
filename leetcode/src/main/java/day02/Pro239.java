package day02;

import java.util.Arrays;

/**
 * @author qyl
 * @program Pro239.java
 * @Description 最小栈、线段树
 * @createTime 2022-08-25 13:36
 */
public class Pro239 {
    public static void main(String[] args) {
        System.out.println(Arrays.toString(new Solution239().maxSlidingWindow(new int[]{1, 3, -1, -3, 5, 3, 6, 7}, 3)));
    }
}


class Solution239 {
    public int[] maxSlidingWindow(int[] nums, int k) {
        int len = nums.length;
        int[] res = new int[len - k + 1];
        int[] arry = new int[len + 1];
        for (int i = 0; i < len; i++) {
            arry[i + 1] = nums[i];
        }
        SegTree[] tree = new SegTree[len << 2];
        init(tree);

        build(tree, arry, 1, 1, len);

        for (int i = 1; i + k -1 <= len; i++) {
            res[i - 1] = query(tree, 1, i, i + k - 1);
        }

        return res;
    }

    void init(SegTree[] tree) {
        for (int i = 0; i < tree.length; i++) {
            tree[i] = new SegTree();
        }
    }

    int pushup(SegTree[] segTrees, int l, int r) {
        return Math.max(segTrees[l].mxValue, segTrees[r].mxValue);
    }

    void build(SegTree[] segTrees, int[] nums, int o, int l, int r) {
        segTrees[o].left = l;
        segTrees[o].right = r;
        if (l == r) {
            segTrees[o].mxValue = nums[l];
            return;
        }
        int mid = l + r >> 1;
        int left = o << 1, right = o << 1 | 1;
        build(segTrees, nums, left, l, mid);
        build(segTrees, nums, right, mid + 1, r);
        segTrees[o].mxValue = pushup(segTrees, left, right);
    }

    int query(SegTree[] segTrees, int o, int left, int right) {
        if (left <= segTrees[o].left && segTrees[o].right <= right) {
            return segTrees[o].mxValue;
        }
        int res = -0x3f3f3f3f;
        int mid = segTrees[o].left + segTrees[o].right >> 1;
        if (left <= mid) {
            res = query(segTrees, o << 1, left, right);
        }
        if (mid < right) {
            res = Math.max(res, query(segTrees, o << 1 | 1, left, right));
        }
        return res;
    }
}

class SegTree {
    int left, right, mxValue;

    public SegTree() {
        this.mxValue = -0x3f3f3f3f;
    }
}