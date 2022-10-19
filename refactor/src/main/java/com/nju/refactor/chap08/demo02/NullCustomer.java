package com.nju.refactor.chap08.demo02;

/**
 * @description
 * @date:2022/10/19 11:27
 * @author: qyl
 */
public class NullCustomer extends Customer {
    @Override
    public boolean isNull(){
        return true;
    }

    @Override
    public String getName(){
        return "occupant";
    }
}
