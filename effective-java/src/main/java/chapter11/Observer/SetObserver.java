package chapter11.Observer;

/**
 * @description
 * @date:2022/9/7 16:30
 * @author: qyl
 */
@FunctionalInterface
public interface SetObserver<E> {
    void added(ObservableSet<E> set, E element);
}
