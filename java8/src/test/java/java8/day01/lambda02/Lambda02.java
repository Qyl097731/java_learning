package java8.day01.lambda02;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * projectName:  java_learing
 * packageName: java8.day01.Lambda02
 * date: 2022-04-02 23:01
 * copyright(c) 2020 南晓18卓工 邱依良
 *
 * @author 邱依良
 */
public class Lambda02 {

    //需求 consumer<T> 消费性接口
    @Test
    public void test01() {
        happy(1000.0, money -> System.out.println(money + " ::: hello"));
    }

    public void happy(double money, Consumer<Double> con) {
        con.accept(money);
    }

    //Supplier<T> 供给接口
    @Test
    public void test02() {
        getNumList(3, () -> (int) (Math.random() * 10)).forEach(System.out::println);
    }

    public List<Integer> getNumList(int num, Supplier<Integer> sup) {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < num; i++) {
            Integer n = sup.get();
            list.add(n);
        }
        return list;
    }

    //Function<T,R>函数接口
    @Test
    public void test03() {
        //除去首位空白
        System.out.println(strHandler("\t\t\thappy", String::trim));
        //大写
        System.out.println(strHandler("abc", String::toUpperCase));
    }

    public String strHandler(String str, Function<String, String> fun) {
        return fun.apply(str);
    }

    @Test
    public void test04(){
        List<String>list = Arrays.asList("hello","qyl","ok");
        System.out.println(filterStr(list, s -> s.length() > 2));
    }
    //Predicate<T> 断言接口
    public List<String> filterStr(List<String>list, Predicate<String> pre){
        List<String>strings = new ArrayList<>();
        for(String str:list){
            if (pre.test(str)){
                strings.add(str);
            }
        }
        return strings;
    }


}
