package java8.day01.SelectEmployee;

/**
 * projectName:  java_learing
 * packageName: Java8.day01
 * date: 2022-04-02 08:52
 * copyright(c) 2020 南晓18卓工 邱依良
 *
 * @author 邱依良
 */
public interface MyFilter<T> {
    boolean check(T t);
}
