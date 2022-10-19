package com.nju.refactor.chap08.demo02;

/**
 * @description
 * @date:2022/10/19 11:26
 * @author: qyl
 */
public class Customer implements Nullable {

    private String name;

    @Override
    public boolean isNull() {
        return false;
    }

    protected Customer(){}

    static Customer newNull(){return new NullCustomer();}


    public String getName() {
        return name;
    }

}
