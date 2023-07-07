package com.nju.factory.abstractfactory;

/**
 * 水果产品族中的产品等级1
 **/
public class Orange extends Fruit{
    @Override
    public void ripeCycle() {
        System.out.println("橘子的成熟周期是10个月");
    }
}
