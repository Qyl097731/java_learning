package common;

/**
 * @author qyl
 * @program PreTreeNode.java
 * @Description 前缀树
 * @createTime 2022-08-30 13:39
 */
public class PreTreeNode {
    public PreTreeNode[] child;
    public int num;

    public PreTreeNode( int num) {
        this.child = new PreTreeNode[26];
        this.num = num;
    }
}
