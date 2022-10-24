package chapter03.demo5;

import chapter03.demo3.ReturnDigest;
import chapter03.demo3.ReturnDigestUserInterface;

/**
 * @description
 * @date:2022/10/24 8:10
 * @author: qyl
 */
public class JoinDigestUserInterface {
    public static void main(String[] args) throws InterruptedException {
        ReturnDigest[] digests = new ReturnDigest[args.length];

        for (int i = 0; i < args.length; i++) {
            digests[i] = new ReturnDigest(args[i]);
            digests[i].start();
        }

        for (int i = 0; i < args.length; i++) {
            digests[i].join();
            ReturnDigest.printResult(args[i], digests[i].getDigest());
        }
    }
}
