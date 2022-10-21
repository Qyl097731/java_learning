package chapter03.demo4;

import java.util.concurrent.Callable;

/**
 * @description 向ExecutorService提交一个Callable任务，每个Callable任务返回一个Future
 * @date:2022/10/21 23:26
 * @author: qyl
 */
public class FindMaxTask implements Callable<Integer> {
    private int[] data;
    private int start;
    private int end;

    public FindMaxTask(int[] data, int start, int end) {
        this.data = data;
        this.start = start;
        this.end = end;
    }

    /**
     * 查找数组的一个分段的最大值
     * @return
     * @throws Exception
     */
    @Override
    public Integer call() throws Exception {
        int max = Integer.MIN_VALUE;
        for (int i = start; i < end ; i++) {
            max = Math.max(max,data[i]);
        }
        return max;
    }
}
