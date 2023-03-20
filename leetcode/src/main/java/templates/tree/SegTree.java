package templates.tree;

import org.junit.jupiter.api.Test;

/**
 * @description 线段树
 * @date 2023/3/18 9:09
 * @author: qyl
 */
public class SegTree {
    @Test
    public void test(){
        int N = (int) (1e6+4);
        SegTree[] tree = new SegTree[N << 2];
    }

    int left, right, mxValue;

    public SegTree() {
        this.mxValue = -0x3f3f3f3f;
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
