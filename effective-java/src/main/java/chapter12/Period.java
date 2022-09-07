package chapter12;

import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.Date;

/**
 * @description
 * @date:2022/9/7 22:38
 * @author: qyl
 */
public final class Period implements Serializable {
    private Date start;
    private Date end;

    public Period(Date start, Date end) {
        this.start = new Date(start.getTime());
        this.end = new Date(end.getTime());
        if (this.start.compareTo(this.end) > 0) {
            throw new IllegalArgumentException(start + "after" + end);
        }
    }

    public Date start() {
        return new Date(start.getTime());
    }

    public Date end() {
        return new Date(end.getTime());
    }

    @Override
    public String toString() {
        return start + "-" + end;
    }

/*  保护性拷贝
    private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
        s.defaultReadObject();

        start = new Date(start.getTime());
        end = new Date(end.getTime());

        if (start.compareTo(end) > 0){
            throw new InvalidObjectException(start + "after" + end);
        }
    }*/

    private Object writeReplace(){
        return new SerializationProxy(this);
    }

    private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
        throw new InvalidObjectException("Proxy required");
    }

    private Object readResolve(){
        return new Period(start,end);
    }

    private static class SerializationProxy implements Serializable {
        private final Date start;
        private final Date end;


        public SerializationProxy(Period period) {
            this.start = period.start();
            this.end = period.end();
        }
        private static final long serialVersionUID = 1;
    }

}
