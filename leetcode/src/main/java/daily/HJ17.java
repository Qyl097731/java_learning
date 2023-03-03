package daily;

import java.util.Scanner;

/**
 * @description
 * @date 2023/3/3 10:08
 * @author: qyl
 */
public class HJ17 {
    public static void main(String[] args) {
        Scanner in = new Scanner (System.in);
        String string = in.nextLine ();
        String[] stringArray = string.split (";");
        int x = 0, y = 0;
        for (String s : stringArray) {
            if (s.matches ("^[ASWD]{1}\\d+$")) {
                char dir = s.charAt (0);
                int distance = Integer.parseInt (s.substring (1));
                if (dir == 'A') {
                    x -= distance;
                } else if (dir == 'S') {
                    y -= distance;
                } else if (dir == 'D') {
                    x += distance;
                } else {
                    y += distance;
                }
            }
        }
        System.out.println (x + "," + y);
    }
}
