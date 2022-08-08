package chapter06;

/**
 * projectName:  jvm
 * packageName: chapter06
 * date: 2022-07-24 15:44
 * author 邱依良
 */
public class EdenSurvivorTest {
    public static void main(String[] args) {
        System.out.println("我只是来打个酱油~");
        try{
            Thread.sleep(100000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
