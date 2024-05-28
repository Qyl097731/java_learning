package com.nju.jdk9;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.VarHandle;

/**
 * @description
 * @date 2024/5/20 17:48
 * @author: qyl
 */
public class VariableHandleExample {
    // 声明实例字段和静态变量句柄
    private int x;
    private static VarHandle X_HANDLE;

    // 声明数组和数组元素句柄
    private final int[] array = new int[10];
    private static VarHandle ARRAY_HANDLE;

    static {
        try {
            // 初始化实例字段的变量句柄
            X_HANDLE = MethodHandles.lookup ().findVarHandle (VariableHandleExample.class, "x", int.class);
            // 初始化数组元素的变量句柄
            ARRAY_HANDLE = MethodHandles.arrayElementVarHandle(int[].class);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace ();
        }
    }

    // 使用变量句柄获取实例字段x的值
    public int getX() {
        return (int) X_HANDLE.get(this);
    }

    // 使用变量句柄设置实例字段x的值
    public void setX(int value) {
        X_HANDLE.set(this, value);
    }

    // 使用变量句柄进行实例字段x的原子比较并交换操作
    public boolean compareAndSetX(int expected, int newValue) {
        return X_HANDLE.compareAndSet(this, expected, newValue);
    }

    // 使用变量句柄获取数组元素的值
    public int getElement(int index) {
        return (int) ARRAY_HANDLE.get(array, index);
    }

    // 使用变量句柄设置数组元素的值
    public void setElement(int index, int value) {
        ARRAY_HANDLE.set(array, index, value);
    }

    public static void main(String[] args) {
        VariableHandleExample example = new VariableHandleExample();

        // 设置和获取实例字段x的值
        example.setX(42);
        System.out.println("Value of x: " + example.getX()); // 输出：Value of x: 42

        // 原子比较并交换操作
        boolean updated = example.compareAndSetX(42, 100);
        System.out.println("Updated: " + updated); // 输出：Updated: true
        System.out.println("Value of x: " + example.getX()); // 输出：Value of x: 100

        // 设置和获取数组元素的值
        example.setElement(0, 123);
        System.out.println("Value at index 0: " + example.getElement(0)); // 输出：Value at index 0: 123
    }
}
