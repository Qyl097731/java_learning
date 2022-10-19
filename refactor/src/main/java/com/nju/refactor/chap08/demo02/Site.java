package com.nju.refactor.chap08.demo02;

/**
 * @description
 * @date:2022/10/19 11:30
 * @author: qyl
 */
public class Site {
    private Customer customer;

    public Customer getCustomer() {
        return (customer == null) ?
                Customer.newNull():
                customer;
    }
}
