package com.nju.jdk14;


import java.lang.reflect.RecordComponent;
import java.util.Arrays;

/**
 * @description
 * @date 2024/6/5 0:10
 * @author: qyl
 */
public class RecordsExamples {
    public static void main(String[] args) {
        RecordComponent[] components = Point.class.getRecordComponents ();
        Arrays.stream (components).forEach (System.out::println);

        System.out.println (Point.class.isRecord ());
    }
}

//class Point{
//
//    private final int x;
//    private final int y;
//
//    public Point(int x, int y) {
//        this.x = x;
//        this.y = y;
//    }
//
//    public int getX() {
//        return x;
//    }
//
//    public int getY() {
//        return y;
//    }
//
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        Point range = (Point) o;
//        return x == range.x && y == range.y;
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(x, y);
//    }
//
//    @Override
//    public String toString() {
//        return "Range{" +
//                "x =" + x +
//                ", y =" + y +
//                "}";
//    }
//}

record Point(int x, int y) {
    static String word = "hello";
    public static String sayHello(){
        return word;
    }

    class InnerClass{ }

    public int calculate(){
        return x * x + y * y;
    }
}
