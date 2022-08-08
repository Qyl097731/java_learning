package chapter13;

import java.lang.reflect.Member;

/**
 * @description
 * @date:2022/8/4 23:51
 * @author: qyl
 */
public class Memory {
    public static void main(String[] args) {
        int i = 1;
        Object o = new Object();
        Memory memory = new Memory();
        memory.fool(o);
    }
    private void fool(Object param){
        String str = param.toString();
        System.out.println(str);
    }
}
