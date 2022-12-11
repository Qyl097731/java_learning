package daily;

import java.util.Arrays;

/**
 * @description
 * @date:2022/12/11 22:45
 * @author: qyl
 */
public class Solution6259 {
    public static void main(String[] args) {
        Allocator allocator = new Allocator (10);
    }

}

class Allocator {

    int[] alloc;
    int siz;

    public Allocator(int n) {
        alloc = new int[n];
        siz = n;
    }

    public int allocate(int size, int mID) {
        int sum = 0;
        for (int i = 0; i < this.siz; i++) {
            if (alloc[i] == 0) {
                sum++;
            } else {
                sum = 0;
            }
            if (sum == size) {
                for (int j = 0; j < size; j++) {
                    alloc[i - j] = mID;
                }
                System.out.println (Arrays.toString (alloc));
                return sum - size;
            }
        }
        return -1;
    }

    public int free(int mID) {
        int tot = 0;
        for (int i = 0; i < siz; i++) {
            if (alloc[i] == mID) {
                alloc[i] = 0;
                tot++;
            }
        }
        return tot;
    }
}
