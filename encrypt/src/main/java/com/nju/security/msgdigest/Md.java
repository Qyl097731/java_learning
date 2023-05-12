package com.nju.security.msgdigest;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import org.bouncycastle.crypto.Digest;
import org.bouncycastle.crypto.digests.MD2Digest;
import org.bouncycastle.crypto.digests.MD4Digest;
import org.bouncycastle.crypto.digests.MD5Digest;

import java.security.MessageDigest;

/**
 * @description MD 加密算法
 * @date 2023/5/12 14:51
 * @author: qyl
 */
public class Md {
    private static final String SOURCE = "nju qiuyiliang test";

    public static void main(String[] args) {

        ccMD2 ();
        bcMD2 ();
        jdkMD2 ();

        bcMD4 ();

        ccMD5();
        bcMD5 ();
        jdkMD5 ();
    }

    public static void jdkMD5() {
        try {
            MessageDigest md5 = MessageDigest.getInstance ("MD5");
            byte[] md5Bytes = md5.digest (SOURCE.getBytes ());
            System.out.println ("JDK MD5 " + Hex.encodeHexString (md5Bytes));
        } catch (Exception e) {
            e.printStackTrace ();
        }
    }

    public static void jdkMD2() {
        try {
            MessageDigest md2 = MessageDigest.getInstance ("MD2");
            byte[] md2Bytes = md2.digest (SOURCE.getBytes ());
            System.out.println ("JDK MD2 " + Hex.encodeHexString (md2Bytes));
        } catch (Exception e) {
            e.printStackTrace ();
        }
    }

    public static void bcMD2() {
        try {
            byte[] sourceBytes = SOURCE.getBytes ();
            Digest digest = new MD2Digest ();
            digest.update (sourceBytes,0, sourceBytes.length);
            byte[] md2Bytes = new byte[digest.getDigestSize ()];
            digest.doFinal (md2Bytes, 0);
            System.out.println ("BC MD2 " + org.bouncycastle.util.encoders.Hex.toHexString (md2Bytes));
        } catch (Exception e) {
            e.printStackTrace ();
        }
    }

    public static void bcMD4() {
        try {
            byte[] sourceBytes = SOURCE.getBytes ();
            Digest digest = new MD4Digest ();
            digest.update (sourceBytes,0, sourceBytes.length);
            byte[] md4Bytes = new byte[digest.getDigestSize ()];
            digest.doFinal (md4Bytes, 0);
            System.out.println ("BC MD4 " + org.bouncycastle.util.encoders.Hex.toHexString (md4Bytes));
        } catch (Exception e) {
            e.printStackTrace ();
        }
    }

    public static void bcMD5() {
        try {
            byte[] sourceBytes = SOURCE.getBytes ();
            Digest digest = new MD5Digest ();
            digest.update (sourceBytes,0, sourceBytes.length);
            byte[] md5Bytes = new byte[digest.getDigestSize ()];
            digest.doFinal (md5Bytes, 0);
            System.out.println ("BC MD5 " + org.bouncycastle.util.encoders.Hex.toHexString (md5Bytes));
        } catch (Exception e) {
            e.printStackTrace ();
        }
    }

    public static void ccMD5(){
        try {
            System.out.println ("CC MD5 " + DigestUtils.md5Hex (SOURCE.getBytes ()));
        }catch (Exception e){
            e.printStackTrace ();
        }
    }

    public static void ccMD2(){
        try {
            System.out.println ("CC MD2 " + DigestUtils.md2Hex (SOURCE.getBytes ()));
        }catch (Exception e){
            e.printStackTrace ();
        }
    }
}
