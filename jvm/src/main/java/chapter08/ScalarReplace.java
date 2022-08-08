package chapter08;

/**
 * projectName:  jvm
 * packageName: chapter08
 * date: 2022-07-24 19:18
 * author 邱依良
 */
public class ScalarReplace {
    public static class User {
        public int id;
        public String name;
    }

    public static void alloc() {
        User u = new User();
        u.id = 5;
        u.name = "njxzc";
    }

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        for (int i = 0; i < 1000000; i++) {
            alloc();
        }
        long end = System.currentTimeMillis();
        System.out.println((end - start) + "ms");
    }
}
