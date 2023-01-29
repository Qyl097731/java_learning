package trails;

import java.util.Arrays;
import java.util.List;

/**
 * @description
 * @date:2023/1/29 14:51
 * @author: qyl
 */
public class Computer {
    public static void main(String[] args) {
        List<Double> numsI = Arrays.asList (4.0, 7.0, 5.0);
        double i = computeI (numsI);
        System.out.println (i);
        List<Double[]> numsE = Arrays.asList (new Double[]{3.0, 2.0, 0.0}, new Double[]{0.0, 4.0, 1.0},
                new Double[]{1.0, 1.0, 4.0});
        double e = computeE (numsE,numsI);
        System.out.println (e);
        System.out.println (i - e);
    }

    static double computeE(List<Double[]> sets, List<Double> numsI) {
        double sum = 0;
        for (Double num : numsI) {
            sum += num;
        }
        double e = 0;
        for (Double[] set : sets) {
            double temp = 0;
            for (Double num : set) {
                temp += num;
            }
            e += temp / sum * computeI (Arrays.asList (set));
        }
        return e;
    }

    static double computeI(List<Double> numsI) {
        double sum = 0;
        for (Double num : numsI) {
            sum += num;
        }
        double res = 0;
        for (Double num : numsI) {
            res -= num/ sum * log2 (num/sum);
        }
        return res;
    }

    static double log2(double x) {
        if (x == 0){
            return 0;
        }
        return Math.log (x) / Math.log (2);
    }
}
