package trails;

/**
 * @description
 * @date:2023/1/22 20:45
 * @author: qyl
 */
public class ParameterTest {
    public static void main(String[] args) {
//        arrayTest ();
        stringTest();

    }

    private static void stringTest() {
        String li = "li",zhang = "zhang";
        System.out.println (System.identityHashCode (zhang));
        System.out.println (System.identityHashCode ("zhang"));
        swap(li,zhang);
        System.out.println (li + " " + zhang);
        System.out.println (System.identityHashCode (li));
    }

    private static void swap(String li, String zhang) {
        String temp = li;
        li = zhang;
        zhang = temp;
        System.out.println (System.identityHashCode (zhang));
    }


    private static void arrayTest() {
        int[] arr = {1, 2, 3, 4};
        System.out.println ("before change : " + arr[0]);
        change (arr);
        System.out.println (System.identityHashCode (arr));
        System.out.println ("after change: " + arr[0]);
    }

    private static void change(int[] arr) {
        System.out.println (System.identityHashCode (arr));
        arr[0] = 2;
    }
}
