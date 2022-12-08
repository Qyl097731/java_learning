package daily;

import java.text.DecimalFormat;

/**
 * @description
 * @date:2022/12/8 17:54
 * @author: qyl
 */
public class Solution2288 {
    public String discountPrices(String sentence, int discount) {
        DecimalFormat df = new DecimalFormat ("0.00");
        String[] strs = sentence.split (" ");
        StringBuilder sb = new StringBuilder ( );
        for (String s : strs) {
            if (check (s)) {
                double v = Double.parseDouble (s.substring (1));
                sb.append ("$").append (df.format (v - v * discount / 100));
            } else {
                sb.append (s);
            }
            sb.append (" ");
        }
        return sb.toString ( ).trim ( );

    }

    private boolean check(String s) {
        if (s.charAt (0) != '$' || s.length ( ) == 1) {
            return false;
        }

        for (int i = 1; i < s.length ( ); i++) {
            if (!Character.isDigit (s.charAt (i))) {
                return false;
            }
        }
        return true;
    }
}
