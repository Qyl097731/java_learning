package daily;

/**
 * @description
 * @date 2023/3/17 23:16
 * @author: qyl
 */
public class Offer33 {
    public boolean verifyPostorder(int[] postorder) {
        return isPost (postorder, 0, postorder.length - 1);
    }

    private boolean isPost(int[] postorder, int left, int right) {
        if (left >= right) {
            return true;
        }
        for (int i = left; i < right; i++) {
            if (postorder[i] > postorder[right]) {
                boolean flag = true;
                for (int j = i; j < right; j++) {
                    flag &= postorder[j] > postorder[right];
                }
                return flag && isPost (postorder, left, i - 1) && isPost (postorder, i, right - 1);
            }
        }
        return isPost (postorder, left, right - 1);
    }
}
