package java8.day01.lambda01;

import org.junit.jupiter.api.Test;

/**
 * projectName:  java_learing
 * packageName: java8.day01.Lambda
 * date: 2022-04-02 20:34
 * copyright(c) 2020 南晓18卓工 邱依良
 *
 * @author 邱依良
 */
public class Lambda {
    @Test
    public void test01(){
        int num = 0;
        Runnable r = new Runnable() {
            @Override
            public void run() {
                System.out.println(num);
            }
        };
        r.run();

        System.out.println("----------------");

        Runnable r1 = () -> System.out.println("Hello");
        r1.run();
    }



}
