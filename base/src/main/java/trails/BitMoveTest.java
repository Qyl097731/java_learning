package trails;

/**
 * @description
 * @date:2023/1/20 21:44
 * @author: qyl
 */
public class BitMoveTest {
    public static void main(String[] args) {
        System.out.println (1>>>42 == 1>>>10);
        System.out.println ("1 = " + Integer.toBinaryString (1));
        System.out.println ("1>>>42 = " + Integer.toBinaryString (1>>>42));
        System.out.println ("-1 = " + Integer.toBinaryString (-1));
        System.out.println (-1>>42 == -1>>10);
        System.out.println (-1>>>10);
        System.out.println ("-1>>10 = " + Integer.toBinaryString (-1>>10));
        System.out.println ("-1>>>10 = " + Integer.toBinaryString (-1>>>10));
    }
}
