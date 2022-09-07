package chapter12;

import org.junit.Test;

import java.io.*;
import java.util.Date;

/**
 * @description 保护性readObject
 * @date:2022/9/7 22:38
 * @author: qyl
 */
public class MutablePeriod {
    public final Period period;
    public final Date start;
    public final Date end;

    public MutablePeriod() {
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bos);

            oos.writeObject(new Period(new Date(), new Date()));

            byte[] ref = {0x71, 0, 0x7e, 0, 5};
            bos.write(ref);
            ref[4] = 4;
            bos.write(ref);

            ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(bos.toByteArray()));
            period = (Period) ois.readObject();
            start = (Date) ois.readObject();
            end = (Date) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new AssertionError(e);
        }
    }

    @Test
    public void test() {
        MutablePeriod mp = new MutablePeriod();
        Period p = mp.period;
        Date end = mp.end;
        end.setYear(78);
        System.out.println(p);
    }
}
