package chapter08;

/**
 * projectName:  jvm
 * packageName: chapter08
 * date: 2022-07-24 19:00
 * author 邱依良
 */
public class StackAllocation {
    public static void main(String[] args) {
        long start = System.currentTimeMillis();

        for (int i = 0 ; i < 10000000;i++){
            alloc();
        }

        long end = System.currentTimeMillis();

        System.out.println("花费的时间为: " + (end - start) + "ms");

        try {
            Thread.sleep(1000000);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private static void alloc() {
        User user = new User();
    }

    static class User{

    }
}
