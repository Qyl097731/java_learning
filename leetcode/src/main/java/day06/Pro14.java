package day06;

import common.PreTreeNode;

/**
 * @author qyl
 * @program Pro14.java
 * @Description 最长公共前缀
 * @createTime 2022-08-30 13:38
 */
public class Pro14 {
    public static void main(String[] args) {
        System.out.println(new Solution14().longestCommonPrefix(new String[]{"flower", "flow", "flight"}));
    }
}

class Solution14 {
    public String longestCommonPrefix(String[] strs) {
        int len = strs.length;
        PreTreeNode root = new PreTreeNode( 0);
        for (String str : strs) {
            build(str, root);
        }
        return longestCommonPrefix(root,len,new StringBuilder());
    }

    public void build(String s, PreTreeNode root) {
        PreTreeNode cur = root;
        for (int i = 0; i < s.length(); i++) {
            int c = s.charAt(i) - 'a';
            if (cur.child[c] == null) {
                cur.child[c] = new PreTreeNode(1);
            }else {
                cur.child[c].num++;
            }
            cur = cur.child[c];
        }
    }

    public String longestCommonPrefix(PreTreeNode root,int total,StringBuilder res){
        for (int i = 0; i < 26; i++) {
            if (root.child[i] != null && root.child[i].num == total){
                res.append((char)('a'+i));
                longestCommonPrefix(root.child[i],total,res);
            }
        }
        return res.toString();
    }

}