package chapter02;

/**
 * projectName:  jvm
 * packageName: chapter02
 * date: 2022-07-22 21:28
 * author 邱依良
 */
public class ClassLoaderTest2 {
    public static void main(String[] args) {
        try{
            //1
            ClassLoader classLoader = Class.forName("java.lang.String").getClassLoader();
            System.out.println(classLoader);

            //2
            classLoader = Thread.currentThread().getContextClassLoader();
            System.out.println(classLoader);

            //3
            classLoader = ClassLoader.getSystemClassLoader().getParent();
            System.out.println(classLoader);
        }catch(ClassNotFoundException e){
            e.printStackTrace();
        }

    }
}
