package daily;

/**
 * @description
 * @date:2022/12/18 17:17
 * @author: qyl
 */
public class Offer58 {
    public String reverseLeftWords(String s, int n) {
        String res = s + s;
        return res.substring (n, s.length ( ) + n);
    }
}
