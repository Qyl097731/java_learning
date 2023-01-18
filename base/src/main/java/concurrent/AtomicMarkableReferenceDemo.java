package concurrent;

import java.util.concurrent.atomic.AtomicMarkableReference;

/**
 * @description
 * @date:2023/1/18 22:13
 * @author: qyl
 */
public class AtomicMarkableReferenceDemo {
    public static void main(String[] args) {
        final Boolean initialRef = null, initialMark = false;
        final AtomicMarkableReference<Boolean> amr = new AtomicMarkableReference<>(initialRef, initialMark);
        System.out.println ("currentValue=" + amr.getReference () + ", currentMark=" + amr.isMarked ());

        //CAS
        final Boolean newReference1 = true,newMark1 = true;
        final boolean casResult = amr.compareAndSet (initialRef,newReference1,initialMark,newMark1);
        System.out.println ("currentValue=" + amr.getReference ()
                + ", currentMark=" + amr.isMarked ()
                + ", casResult=" + casResult);

        // 获取当前得值和当前得mark值
        boolean[] arr = new boolean[1];
        final Boolean currentValue = amr.get (arr);
        final boolean currentMark = arr[0];
        System.out.println ("currentValue = " + currentValue + ", currentMark = " + currentMark);

        // 单独设置stamp
        final boolean attemptMarkResult = amr.attemptMark (newReference1,false);
        System.out.println ("currentValue=" + amr.getReference ()
                + ", currentMark=" + amr.isMarked ()
                + ", attemptMarkResult=" + attemptMarkResult);

        // 重新设置当前值和mark值
        amr.set (initialRef,initialMark);
        System.out.println ("currentValue=" + amr.getReference () + ", currentMark=" + amr.isMarked ());
    }
}
