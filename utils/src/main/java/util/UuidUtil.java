package util;
import java.util.UUID;
/**
 * @author qyl
 * @program UuidUtil.java
 * @Description uuid工具类
 * @createTime 2022-08-25 17:26
 */
public class UuidUtil {
    public UuidUtil() {
    }

    public static String get32UUID() {
        String uuid = UUID.randomUUID().toString().trim().replaceAll("-", "");
        return uuid;
    }

    public static void main(String[] args) {
        System.out.println(get32UUID());
    }
}
