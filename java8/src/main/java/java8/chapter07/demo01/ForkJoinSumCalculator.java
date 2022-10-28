package java8.chapter07.demo01;

import org.junit.jupiter.api.Test;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;
import java.util.stream.LongStream;

/**
 * @description
 * @date:2022/10/28 22:52
 * @author: qyl
 */
public class ForkJoinSumCalculator extends RecursiveTask<Long> { // 继 承 RecursiveTask 来创建可以用于分支/合并框架的任务
    private final long[] numbers;
    private final int start;
    private final int end;

    private static final long THRESHOLD = 10000;

    public ForkJoinSumCalculator(long[] numbers) {
        this(numbers, 0, numbers.length);
    }

    public ForkJoinSumCalculator(long[] numbers, int start, int end) {
        this.numbers = numbers;
        this.start = start;
        this.end = end;
    }

    // 该任务负责求和的部分的大小
    @Override
    protected Long compute() {
        int length = end - start;
        if (length <= THRESHOLD) {
            return computeSequentially();               // 小于阈值 顺序执行
        }
        ForkJoinSumCalculator leftTask = new ForkJoinSumCalculator(numbers, start, start + length / 2);
        // 计算左边数组的任务放入当前线程池
        leftTask.fork();
        ForkJoinSumCalculator rightTask = new ForkJoinSumCalculator(numbers, start + length / 2, end);
        // 创建一个任务为数组的后一半求和
        Long rightResult = rightTask.compute();
        Long leftResult = leftTask.join();
        return rightResult + leftResult;
    }

    private long computeSequentially() {
        long sum = 0 ;
        for (int i = start ;i < end;i++){
            sum +=  numbers[i];
        }
        return sum;
    }

}
class MyTest{
    @org.junit.jupiter.api.Test
    public void test(){
        long[] nums = LongStream.rangeClosed(1,100000).toArray();
        ForkJoinTask<Long> task = new ForkJoinSumCalculator(nums);
        System.out.println(new ForkJoinPool().invoke(task));
    }
}
