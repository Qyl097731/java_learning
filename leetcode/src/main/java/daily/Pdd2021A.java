package daily;

import java.io.*;

/**
 * @description
 * @date 2023/3/8 23:42
 * @author: qyl
 */
public class Pdd2021A {
    static int result = 0;

    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader (new InputStreamReader (System.in));
        BufferedWriter out = new BufferedWriter (new OutputStreamWriter (System.out));

        int n = Integer.parseInt (in.readLine ());
        if (n > 45) {
            out.write (-1 + "\n");
        } else {
            int index = 9;
            int[] nums = new int[10];
            for (int i = 9; i >= 0; i--) {
                if (n >= i) {
                    nums[index--] = i;
                    n -= i;
                }
                if (n == 0) {
                    break;
                }
            }
            while (index < 10) {
                result = result * 10 + nums[index++];
            }
            out.write (result + "\n");
        }
        out.flush ();
    }
}
