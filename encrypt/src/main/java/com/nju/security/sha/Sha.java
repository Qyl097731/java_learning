package com.nju.security.sha;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.codec.digest.MessageDigestAlgorithms;
import org.bouncycastle.crypto.Digest;
import org.bouncycastle.crypto.digests.SHA1Digest;
import org.bouncycastle.crypto.digests.SHA224Digest;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @description SHA 算法
 * @date 2023/5/12 16:42
 * @author: qyl
 */
public class Sha {
    private static final String SOURCE = "nju qiuyiliang test";

    public static void main(String[] args) {
        jdkSHA1 ();
        bcSHA1 ();
        ccSHA1();

        bcSHA224_1();
        bcSHA224_2();
    }

    public static void jdkSHA1() {
        try {
            MessageDigest digest = MessageDigest.getInstance (MessageDigestAlgorithms.SHA_1);
            byte[] result = digest.digest (SOURCE.getBytes ());
            System.out.println ("jdk sha1:" + Hex.encodeHexString (result));
        } catch (Exception e) {
            e.printStackTrace ();
        }
    }

    public static void bcSHA1() {
        byte[] sourceBytes = SOURCE.getBytes ();
        Digest digest = new SHA1Digest ();
        digest.update (sourceBytes, 0, sourceBytes.length);
        byte[] sha1Byte = new byte[digest.getDigestSize ()];
        digest.doFinal (sha1Byte, 0);
        System.out.println ("bc sha1:" + org.bouncycastle.util.encoders.Hex.toHexString (sha1Byte));
    }

    public static void bcSHA224_1(){
        byte[] sourceBytes = SOURCE.getBytes();
        Digest digest = new SHA224Digest ();
        digest.update (sourceBytes,0,sourceBytes.length);
        byte[] sha224Bytes = new byte[digest.getDigestSize()];
        digest.doFinal (sha224Bytes,0);
        System.out.println ("bc sha224 " + org.bouncycastle.util.encoders.Hex.toHexString (sha224Bytes));
    }

    public static void bcSHA224_2(){
        try {
            MessageDigest sha224Digest = MessageDigest.getInstance (MessageDigestAlgorithms.SHA_224,
                    new BouncyCastleProvider ());
            byte[] result = sha224Digest.digest (SOURCE.getBytes ());
            System.out.println ("bc sha224 " + Hex.encodeHexString(result));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException (e);
        }
    }
    public static void ccSHA1() {
        System.out.println ("cc sha1" + DigestUtils.sha1Hex (SOURCE.getBytes ()));
    }

}
