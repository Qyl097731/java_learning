package com.nju.refactor.chap08.constant;

/**
 * @description
 * @date:2022/10/18 23:01
 * @author: qyl
 */
public class FruitTest {

    public static void main(String[] args) {
        System.out.println(Weather.SPRING == Fruit.APPLE);          // false
        System.out.println(Weather.SUMMER == Fruit.ORANGE);         // true
    }
}
