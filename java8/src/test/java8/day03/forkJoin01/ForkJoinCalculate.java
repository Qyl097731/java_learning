package java8.day03.forkJoin01;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.concurrent.RecursiveTask;

/**
 * projectName:  java_learing
 * packageName: java8.day03
 * date: 2022-04-04 15:37
 * copyright(c) 2020 南晓18卓工 邱依良
 *
 * @author 邱依良
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ForkJoinCalculate extends RecursiveTask<Long> {

    private long start;
    private long end;

    private static final long THRESHOLD = 10000;



    @Override
    protected Long compute() {

        long length = end - start;
        long sum = 0;

        if(length <= THRESHOLD){
            for(long i = start; i <= end; i++){
                sum += i;
            }
            return sum;
        }else{
            long mid = (start + end)>>1;
            ForkJoinCalculate left = new ForkJoinCalculate(start,mid);
            left.fork();
            ForkJoinCalculate right = new ForkJoinCalculate(mid+1,end);
            right.fork();

            return left.join() + right.join();
        }
    }
}
