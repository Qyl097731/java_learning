package daily;

import java.util.ArrayList;

/**
 * @description
 * @date:2023/1/7 22:35
 * @author: qyl
 */
public class DataStream {
    int cnt = 0;
    ArrayList<Integer> nums;
    int k;
    int value;

    public DataStream(int value, int k) {
        this.value = value;
        nums = new ArrayList<> ();
        this.k = k;
    }

    public boolean consec(int num) {
        nums.add (num);
        if (num == this.value) {
            cnt++;
        } else {
            cnt = 0;
        }
        return cnt >= k;
    }
}
