package com.nju.concurrent.ch08.demo01;

import net.jcip.annotations.Immutable;

import java.util.LinkedList;
import java.util.List;

/**
 * @description 节点 存放了移动方向，当前位置，和前一个节点位置
 * @date:2022/12/26 17:10
 * @author: qyl
 */
@Immutable
public class Node<P,M> {
    final P pos;
    final M move;
    final Node<P,M> prev;

    public Node(P pos, M move, Node<P, M> prev) {
        this.pos = pos;
        this.move = move;
        this.prev = prev;
    }

    List<M> asMoveList(){
        List<M> solution = new LinkedList<M> ();
        for (Node<P,M> n = this; n.move!=null;n = n.prev) solution.add (0,n.move);
        return solution;
    }
}
