package chapter5;

import java.util.Arrays;
import java.util.EmptyStackException;

/**
 * @author qyl
 * @program GenericStack.java
 * @Description 泛型类
 * @createTime 2022-08-26 14:00
 */
public class GenericStack<E> {
    private E[] elements;
    private int size = 0;
    private static final int DEFAULT_INITIAL_CAPACITY = 16;

    /**
     *     这里确保是安全的 不会出现错误
     */
    @SuppressWarnings("unchecked")
    public GenericStack() {
        elements = (E[]) new Object[DEFAULT_INITIAL_CAPACITY];
    }

    public void push(E e) {
        ensureCapacity();
        elements[size++] = e;
    }

    public E pop() {
        if (size == 0) {
            throw new EmptyStackException();
        }
        E result = elements[--size];
        elements[size] = null;
        return result;
    }

    private void ensureCapacity() {
        elements = Arrays.copyOf(elements, elements.length << 1);
    }
}
