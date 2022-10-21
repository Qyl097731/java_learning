package day29;

import java.util.ArrayDeque;
import java.util.Stack;

/**
 * @description
 * @date:2022/10/21 9:17
 * @author: qyl
 */
class StockSpanner {
    ArrayDeque<int[]> deque ;
    int idx = 0;
    public StockSpanner() {
        deque = new ArrayDeque<>();deque.addLast(new int[]{Integer.MAX_VALUE,-1});
    }

    public int next(int price) {
        int res;
        if (!deque.isEmpty()){
            while (!deque.isEmpty() && deque.peekLast()[0] <= price){
                deque.pollLast();
            }
        }
        res = idx - deque.peekLast()[1];
        deque.addLast(new int[]{price,idx++});
        return res;
    }
}

/**
 * Your StockSpanner object will be instantiated and called as such:
 * StockSpanner obj = new StockSpanner();
 * int param_1 = obj.next(price);
 */
