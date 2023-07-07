package com.nju.spring6.bean;

/**
 * @author 动力节点
 * @version 1.0
 * @className User1
 * @since 1.0
 **/
public class User1 {
    private String name;

    public User1() {
        System.out.println("1.实例化Bean");
    }

    public void setName(String name) {
        this.name = name;
        System.out.println("2.Bean属性赋值");
    }

    public void initBean(){
        System.out.println("3.初始化Bean");
    }

    public void destroyBean(){
        System.out.println("5.销毁Bean");
    }

}
