package com.nju.security.mac;

import org.apache.commons.codec.binary.Hex;
import org.bouncycastle.crypto.digests.MD5Digest;
import org.bouncycastle.crypto.macs.HMac;
import org.bouncycastle.crypto.params.KeyParameter;

import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * @description MAC (HMAC) 使用
 * @date 2023/5/12 17:16
 * @author: qyl
 */
public class Hmac {
    private static final String SOURCE = "nju qiuyiliang test";
    private static byte[] encoded ;

    public static void main(String[] args) {
        jdkHmacMD5();
        bcHmacMD5();
    }

    public static void jdkHmacMD5(){
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance ("HmacMD5");
            encoded = keyGenerator.generateKey ().getEncoded ();
            SecretKey secretKey = new SecretKeySpec (encoded, "HmacMD5");
            Mac mac = Mac.getInstance(secretKey.getAlgorithm());
            mac.init (secretKey);
            byte[] result = mac.doFinal (SOURCE.getBytes ());
            System.out.println ("jdk hmacMD5 " + Hex.encodeHexString (result));
        }catch (Exception e){
            e.printStackTrace ();
        }
    }

    public static void bcHmacMD5(){
        byte[] sourceBytes = SOURCE.getBytes ();
        HMac mac = new HMac (new MD5Digest ());
        mac.init (new KeyParameter (encoded));
        mac.update(sourceBytes, 0, sourceBytes.length);
        // 执行摘要
        byte[] hmacMD5Bytes = new byte[mac.getMacSize ()];
        mac.doFinal (hmacMD5Bytes,0);
        System.out.println ("bc hmacMD5 " + Hex.encodeHexString (hmacMD5Bytes));
    }
}
