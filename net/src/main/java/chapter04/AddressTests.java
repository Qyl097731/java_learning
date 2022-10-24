package chapter04;

import java.net.InetAddress;

/**
 * @description 查看ip地址时IPV4还是IPV6
 * @date:2022/10/24 13:35
 * @author: qyl
 */
public class AddressTests {
    public static int getVersion(InetAddress ia){
        byte[] address = ia.getAddress();
        if (address.length == 4){
            return 4;
        }else if (address.length == 16){
            return 6;
        }else{
            return -1;
        }
    }
}
