package util;
import java.security.MessageDigest;

/**
 * @author qyl
 * @program MD5Utils.java
 * @Description MD5工具类
 * @createTime 2022-08-25 17:25
 */

public class MD5 {
    public MD5() {
    }

    public static String md5(String str) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(str.getBytes());
            byte[] b = md.digest();
            StringBuffer buf = new StringBuffer("");

            for(int offset = 0; offset < b.length; ++offset) {
                int i = b[offset];
                if (i < 0) {
                    i += 256;
                }

                if (i < 16) {
                    buf.append("0");
                }

                buf.append(Integer.toHexString(i));
            }

            str = buf.toString();
        } catch (Exception var6) {
            var6.printStackTrace();
        }

        return str;
    }

    public static String GetSalt() {
        return md5(UuidUtil.get32UUID());
    }

    public static String md5Password(String password, String salt) {
        return md5(password.concat(salt));
    }

    public static void main(String[] args) {
        String password = "123456";
        String salt = GetSalt();
        System.out.println(salt);
        System.out.println(md5Password(password, salt));
    }
}
