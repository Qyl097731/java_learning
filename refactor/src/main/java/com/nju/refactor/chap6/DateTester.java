package com.nju.refactor.chap6;

import java.util.Date;

/**
 * @description
 * @date:2022/10/15 9:16
 * @author: qyl
 */
public class DateTester {
    public static void nextDateUpdate(Date arg) {
        arg.setDate(arg.getDate() + 1);
        System.out.println("in nextDateUpdate date = " + arg);
    }

    public static void nextDateReplace(Date arg) {
        arg = new Date(arg.getYear(),arg.getMonth(),arg.getDate()+1);
        System.out.println("in nextDateReplace date = " + arg);
    }

    public static void main(String[] args) {
        Date date = new Date("1 Apr 98");
        nextDateUpdate(date);
        System.out.println("after nextDateUpdate date = " + date);

        Date date1 = new Date("1 Apr 98");
        nextDateReplace(date1);
        System.out.println("after nextDateReplace date = " + date1);

    }
}
