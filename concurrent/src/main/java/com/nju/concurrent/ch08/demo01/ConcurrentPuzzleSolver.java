package com.nju.concurrent.ch08.demo01;

import java.util.List;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutorService;

/**
 * @description 并发解决 迷宫问题
 * @date:2022/12/26 17:13
 * @author: qyl
 */
public class ConcurrentPuzzleSolver<P, M> {
    private final Puzzle<P, M> puzzle;
    private final ExecutorService exec;
    private final ConcurrentMap<P, Boolean> seen;
    final ValueLatch<Node<P, M>> solution = new ValueLatch<> ();

    public ConcurrentPuzzleSolver(Puzzle<P, M> puzzle, ExecutorService exec, ConcurrentMap<P, Boolean> seen) {
        this.puzzle = puzzle;
        this.exec = exec;
        this.seen = seen;
    }

    public List<M> solve() throws InterruptedException {
        try {
            P p = puzzle.initialPosition ();
            exec.execute (newTask (p, null, null));
            Node<P, M> node = solution.getValue ();
            return node == null ? null : node.asMoveList ();
        }finally {
            exec.shutdown ();
        }
    }

    private Runnable newTask(P p, M m, Node<P, M> n) {
        return new SolverTask(p,m,n);
    }

    class SolverTask extends Node<P,M> implements Runnable{

        public SolverTask(P pos, M move, Node<P, M> prev) {
            super (pos, move, prev);
        }

        @Override
        public void run() {
            // 已经找到解决方案或者该位置曾经到过
            if (solution.isSet () || seen.putIfAbsent (pos,true) != null) return;
            if (puzzle.isGoal (pos)){
                solution.setValue (this);
            }else {
                for (M m : puzzle.legalMoves (pos)) {
                    exec.execute (newTask (puzzle.move (pos,m),m,this));
                }
            }
        }
    }
}
