package java8.day01.lambda01;

import org.junit.jupiter.api.Test;

import java.util.Comparator;

/**
 * projectName:  java_learing
 * packageName: java8.day01.Lambda
 * date: 2022-04-02 20:43
 * copyright(c) 2020 南晓18卓工 邱依良
 *
 * @author 邱依良
 */
class ConsumerTest {

    @Test
    void test01() {
        Consumer<String>con = System.out::println;
        con.accept("hello");
    }
    @Test
    void test02(){
        Comparator<Integer>comparator = (x,y) -> {
            System.out.println(x + y);
            return Integer.compare(x, y);
        };
        if(comparator.compare(1,2) > 0){
            System.out.println("hello");
        }else{
            System.out.println("go");
        }

    }
    @Test
    void test03(){
        System.out.println(strHandler("\t\t\thello", String::trim));

        System.out.println(strHandler("abcdef", String::toUpperCase));

        System.out.println(strHandler("abcdef", str -> str.substring(2, 5)));

    }
    public String strHandler(String str,MyFunction myFunction){
        return myFunction.getValue(str);
    }

    @Test
    void test04(){
        op(1L,2L, Long::sum);

        op(1L,2L,(x,y)->x*y);

    }
    public void op(Long l1,Long l2,MyFunction2<Long,Long>mf){
        mf.getValue(l1,l2);
    }
}
