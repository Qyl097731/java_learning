package chapter06;

/**
 * projectName:  jvm
 * packageName: chapter06
 * date: 2022-07-24 17:56
 * author 邱依良
 */
public class TLABArgsTest {
    public static void main(String[] args) {
        System.out.println("我只是打酱油的~");
        try{
            Thread.sleep(100000);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
