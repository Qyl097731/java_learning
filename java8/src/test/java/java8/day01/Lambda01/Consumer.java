package java8.day01.Lambda01;

/**
 * projectName:  java_learing
 * packageName: java8.day01.Lambda
 * date: 2022-04-02 20:37
 * copyright(c) 2020 南晓18卓工 邱依良
 *
 * @author 邱依良
 */
@FunctionalInterface
public interface Consumer<T> {
    void accept(T t);
}
