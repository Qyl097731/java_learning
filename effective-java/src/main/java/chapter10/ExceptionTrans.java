package chapter10;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * @description
 * @date:2022/9/6 23:01
 * @author: qyl
 */
public class ExceptionTrans<E> {
    List<E> list = new ArrayList<>();

    /**
     * 高层封装底层异常，帮助高层找出错误
     */
    public E get(int index) {
        try {
            return list.get(index);
        } catch (NoSuchElementException e) {
            throw new IndexOutOfBoundsException("Index: " + index);
        }
    }

//    public List<E> list() {
//        try {
//            return list;
//        }catch (LowerLevelException cause){
//            throw new HigherLevelException(cause);
//        }
//    }
}
class LowerLevelException extends Exception {
    LowerLevelException(Throwable cause){
        super(cause);
    }
}

class HigherLevelException extends Exception {
    HigherLevelException(Throwable cause) {
        super(cause);
    }
}
