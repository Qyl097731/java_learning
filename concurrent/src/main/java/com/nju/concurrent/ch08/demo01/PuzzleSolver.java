package com.nju.concurrent.ch08.demo01;

import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @description 能够感知 不存在 solution的封装
 * @date:2022/12/26 17:26
 * @author: qyl
 */
public class PuzzleSolver<P, M> extends ConcurrentPuzzleSolver<P, M> {
    private final AtomicInteger taskCount = new AtomicInteger (0);

    public PuzzleSolver(Puzzle<P, M> puzzle, ExecutorService exec, ConcurrentMap<P, Boolean> seen) {
        super (puzzle, exec, seen);
    }

    protected Runnable newTask(P p, M m, Node<P, M> n) {
        return new CountingSolverTask (p, m, n);
    }

    class CountingSolverTask extends SolverTask {

        public CountingSolverTask(P pos, M move, Node<P, M> prev) {
            super (pos, move, prev);
            taskCount.incrementAndGet ();
        }

        @Override
        public void run() {
            try {
                super.run ();
            } finally {
                if (taskCount.decrementAndGet () == 0) {
                    solution.setValue (null);
                }
            }
        }
    }
}
