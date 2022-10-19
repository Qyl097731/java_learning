package com.nju.refactor.chap08.demo02;

/**
 * @description
 * @date:2022/10/19 11:32
 * @author: qyl
 */
public class SiteTest {
    public static void main(String[] args) {
        Site site = new Site();
        Customer customer = site.getCustomer();
        String customerName;
        customerName = customer.getName();
    }
}
