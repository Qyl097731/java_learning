package daily;

/**
 * @description
 * @date:2023/1/7 22:30
 * @author: qyl
 */
public class Solution6287 {
    public String categorizeBox(int length, int width, int height, int mass) {
//        "Bulky"
//        "Heavy"
//        "Both"
//        "Neither"
        String[] str = {"Neither", "Bulky", "Heavy", "Both"};
        int res = 0;
        if (length >= 10000 || width >= 10000 || height >= 10000 || (long) length * width * height >= (long) 1e9) {
            res |= 1;
        }
        if (mass >= 100) {
            res |= 2;
        }
        return str[res];
    }
}
