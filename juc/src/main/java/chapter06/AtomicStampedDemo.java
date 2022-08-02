package chapter06;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * @author qyl
 * @program AtomicStampedDemo.java
 * @Description TODO
 * @createTime 2022-08-01 15:36
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
class Book {
    private Integer id;
    private String bookName;
}

public class AtomicStampedDemo {
    public static void main(String[] args) {
        Book jave = new Book(1, "jave");

        AtomicStampedReference<Book> stampedReference = new AtomicStampedReference<>(jave, 1);

        System.out.println(stampedReference.getReference() + "\t" + stampedReference.getStamp());

        Book mysql = new Book(2, "mysql");

        stampedReference.compareAndSet(jave, mysql, stampedReference.getStamp(), stampedReference.getStamp() + 1);
        System.out.println(stampedReference.getReference() + "\t" + stampedReference.getStamp());


        stampedReference.compareAndSet(mysql, jave, stampedReference.getStamp(), stampedReference.getStamp() + 1);
        System.out.println(stampedReference.getReference() + "\t" + stampedReference.getStamp());
    }
}
