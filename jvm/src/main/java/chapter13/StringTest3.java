package chapter13;

import java.util.HashSet;
import java.util.Set;

/**
 * @description intern 在常量池生成字符串
 * @date:2022/8/4 23:42
 * @author: qyl
 */
public class StringTest3 {
    public static void main(String[] args) {
        // 使用set保持常量池引用 比米娜full gc回收常量
        Set<String> set = new HashSet<String>();
        // short可以取值内足以让6mb的永久代或者堆溢出
        short i = 0;
        while (true){
            set.add(String.valueOf(i++).intern());
        }
    }
}
