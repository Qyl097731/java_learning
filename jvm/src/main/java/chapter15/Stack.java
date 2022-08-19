package chapter15;

import java.util.Arrays;
import java.util.EmptyStackException;

/**
 * @author qyl
 * @program Stack.java
 * @Description 内存泄露案例
 * @createTime 2022-08-19 13:34
 */
public class Stack {
    private Object[] elements;
    private int size = 0;
    private static final int DEFAULT_CAPACITY = 16;

    public Stack() {
        elements = new Object[DEFAULT_CAPACITY];
    }

    public void push(Object e) {
        ensureCapacity();
        elements[size++] = e;
    }

    // 内存泄露
//    public Object pop() {
//        if (size == 0) {
//            throw new EmptyStackException();
//        }
//        return elements[--size];
//    }

    public Object pop(){
        if (size == 0) {
            throw new EmptyStackException();
        }
        Object top = elements[--size];
        elements[size] = null;
        return top;
    }

    public void ensureCapacity() {
        if (size == DEFAULT_CAPACITY) {
            elements = Arrays.copyOf(elements, size << 1 + 1);
        }
    }
}
