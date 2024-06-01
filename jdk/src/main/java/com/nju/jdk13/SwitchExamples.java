package com.nju.jdk13;

import static java.util.Calendar.*;

/**
 * @description
 * @date 2024/5/30 22:32
 * @author: qyl
 */
public class SwitchExamples {

    private void chooseDayJdk13(int day) {
        int j = switch (day) {
            case MONDAY  -> 0;
            case TUESDAY -> 1;
            default      -> {
                System.out.println ("yield test");
                yield 2;
            }
        };

        int i = switch (day) {
            case MONDAY  : yield 0;
            case TUESDAY : yield 1;
            default :{
                System.out.println ("yield test");
                yield 2;
            }
        };
    }
}


