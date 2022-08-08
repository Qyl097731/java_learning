package chapter10;

/**
 * -Xint 解释器
 * -Xmixed 混合模式
 * -Xcomp JIT编译
 * @date:2022/7/31 0:14
 * @author: qyl
 */
public class IntCompTest {
    public static void main(String[] args) {
        long start = System.currentTimeMillis();

        testPrimeNumber(1000000);

        long end = System.currentTimeMillis();

        System.out.println("花费时间为: " + (end - start));
    }

    public static void testPrimeNumber(int count) {
        for (int i = 0; i < count; i++) {
            for (int j = 2; j <= 100; j++) {
                for(int k = 2; k < Math.sqrt(j);k++){
                    if(j % k == 0){
                        break;
                    }
                }
            }
        }
    }
}
