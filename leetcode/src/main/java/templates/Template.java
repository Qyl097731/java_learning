package templates;

import java.io.*;

/**
 * @description
 * @date:2022/10/23 18:44
 * @author: qyl
 */
public class Template {
    public static void main(String[] args) throws FileNotFoundException {
//        BufferedReader f = new BufferedReader(new FileReader("input.txt"));
//        BufferedReader f = new BufferedReader(new InputStreamReader(System.in));
        try{
            System.setIn(new FileInputStream("input"));
        }catch (Exception e){

        }
    }
}
