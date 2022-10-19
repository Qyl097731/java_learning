package com.nju.refactor.chap08.demo01;

import org.junit.jupiter.api.Test;

import java.util.Optional;

/**
 * @description
 * @date:2022/10/19 10:08
 * @author: qyl
 */
public class Person {
    private final boolean male;
    private final char code;

    public Person(boolean male, char code) {
        this.male = male;
        this.code = code;
    }

    static Person creatMale(){
        return new Person(true,'F');
    }
    static Person createFemale(){
        return new Person(false,'M');
    }

    public boolean isMale() {
        return male;
    }

    public char getCode() {
        return code;
    }
}

