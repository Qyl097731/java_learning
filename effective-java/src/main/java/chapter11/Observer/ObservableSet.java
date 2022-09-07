package chapter11.Observer;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @description
 * @date:2022/9/7 16:23
 * @author: qyl
 */
public class ObservableSet<E> extends ForwardingSet<E> {
    public ObservableSet(Set<E> s) {
        super(s);
    }

    //    private final List<SetObserver<E>> observers = new ArrayList<>();
    private final List<SetObserver<E>> observers = new CopyOnWriteArrayList<>();

    public void addObserver(SetObserver<E> observer) {
//        synchronized (observers) {
        observers.add(observer);
//        }
    }

    public boolean removeObserver(SetObserver<E> observer) {
//        synchronized (observers) {
        return observers.remove(observer);
//        }
    }

    private void notifyElementAdded(E e) {
//        List<SetObserver<E>> snapshot = null;
//        synchronized (observers) {
//            snapshot = new ArrayList<>(observers);
//        }
//        for (SetObserver<E> observer : snapshot) {
//            observer.added(this, e);
//        }
        for (SetObserver<E> observer : observers) {
            observer.added(this, e);
        }
    }


    @Override
    public boolean add(E element) {
        boolean added = super.add(element);
        if (added) {
            notifyElementAdded(element);
        }
        return added;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        boolean result = false;
        for (E element : c) {
            result |= super.add(element);
        }
        return result;
    }
}
