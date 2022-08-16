package chapter02;

/**
 * @description
 * @date:2022/8/14 22:42
 * @author: qyl
 */
public class ClassCastTest {
    public static void main(String[] args) {
        pInfCast();
        nNaNCast();
    }

    public static void pInfCast(){
        double d2 = Double.POSITIVE_INFINITY;
        long l = (long)d2;
        int j = (int)d2;
        System.out.println(l);
        System.out.println(j);
    }

    public static void nNaNCast(){
        double d2 = Double.NaN;
        long l = (long)d2;
        int j = (int)d2;
        System.out.println(l);
        System.out.println(j);
    }
}
