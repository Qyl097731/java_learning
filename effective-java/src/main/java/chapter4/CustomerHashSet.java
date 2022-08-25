package chapter4;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;

/**
 * @author qyl
 * @program CustomerHashSet.java
 * @Description 继承带来的错误
 * @createTime 2022-08-25 17:46
 */
public class CustomerHashSet<T> extends HashSet<T> {
    public static void main(String[] args) {
        CustomerHashSet<Integer> set = new CustomerHashSet<>();
        set.addAll(Arrays.asList(1,2,3));
        System.out.println(set.getAddCount());
    }

    private int addCount = 0;

    public CustomerHashSet() {
        super();
    }

    @Override
    public boolean add(T t) {
        addCount++;
        return super.add(t);
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        addCount += c.size();
        return super.addAll(c);
    }

    public int getAddCount() {
        return addCount;
    }

    public void setAddCount(int addCount) {
        this.addCount = addCount;
    }
}
