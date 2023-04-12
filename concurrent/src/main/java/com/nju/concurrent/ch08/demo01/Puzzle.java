package com.nju.concurrent.ch08.demo01;

import java.util.Set;

/**
 * @description 迷宫
 * @date:2022/12/26 17:13
 * @author: qyl
 */
public interface Puzzle<P,M> {
    P initialPosition();
    boolean isGoal(P position);
    Set<M> legalMoves(P position);
    P move(P position,M move);
}
