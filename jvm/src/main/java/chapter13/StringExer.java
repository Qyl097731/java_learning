package chapter13;

/**
 * @description
 * @date:2022/8/4 23:21
 * @author: qyl
 */
public class StringExer {
    String str = new String("good");
    char[] ch = {'t','e','s','t'};

    public void change(String str,char[] ch){
        str = "test ok";
        ch[0] = 'b';
    }

    public static void main(String[] args) {
        StringExer exer = new StringExer();
        exer.change(exer.str,exer.ch);
        System.out.println(exer.str);
        System.out.println(exer.ch);
    }
}
