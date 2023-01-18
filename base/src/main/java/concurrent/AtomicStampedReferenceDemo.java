package concurrent;

import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * @description
 * @date:2023/1/18 22:02
 * @author: qyl
 */
public class AtomicStampedReferenceDemo {
    public static void main(String[] args) {
        final Integer initialRef = 0, initialStamp = 0;
        final AtomicStampedReference<Integer> asr = new AtomicStampedReference<> (initialRef
                , initialStamp);
        System.out.println ("currentValue = " + asr.getReference () + ", currentStamp = " + asr.getStamp ());

        // CAS
        final Integer newRef = 666, newStamp = 999;
        final boolean casResult = asr.compareAndSet (initialStamp,newRef,initialStamp,newStamp);
        System.out.println("currentValue=" + asr.getReference()
                + ", currentStamp=" + asr.getStamp()
                + ", casResult=" + casResult);

        // 获取当前stamp
        int[] arr = new int[1];
        final Integer currentValue = asr.get (arr);
        final int currentStamp = arr[0];
        System.out.println ("currentValue = " + currentValue + ", currentStamp = " + currentStamp);

        // 单独设置stamp
        final boolean attemptStampResult = asr.attemptStamp (newRef,88);
        System.out.println("currentValue=" + asr.getReference()
                + ", currentStamp=" + asr.getStamp()
                + ", attemptStampResult=" + attemptStampResult);

        // 重新设置当前值和stamp值
        asr.set (initialRef,initialStamp);
        System.out.println ("currentValue = " + asr.getReference () + ", currentStamp = " + asr.getStamp ());
    }
}
