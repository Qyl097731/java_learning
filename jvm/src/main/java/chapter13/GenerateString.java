package chapter13;

import java.io.*;

/**
 * @description
 * @date:2022/8/4 23:30
 * @author: qyl
 */
public class GenerateString {
    public static void main(String[] args) throws IOException {
        try(FileWriter fw = new FileWriter("words.txt")) {

            for (int i = 0; i < 100000; i++) {
                int len = (int) (Math.random() * (10 - 1 + 1) + 1);
                fw.write(getString(len) + "\n");
            }
        }

        try(BufferedReader br = new BufferedReader(new FileReader("words.txt"))){
            long start = System.currentTimeMillis();
            String data;
            while((data = br.readLine()) != null){
                data.intern();
            }
            long end = System.currentTimeMillis();

            System.out.println("花费时间为 : " + (end - start));
        }
    }
    public static String getString(int len){
        String str="";
        for (int i = 0; i < len; i++) {
            int num = (int)(Math.random() * (90 - 65 + 1) + 65) + (int)(Math.random() * 2) * 32;
            str += (char)num;
        }
        return str;
    }

}
