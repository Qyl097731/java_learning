package com.nju.refactor.chap11.demo2;

/**
 * @description
 * @date:2022/10/21 12:48
 * @author: qyl
 */
public class Employee extends Party{
    private int annualCost;
    private String id;

    public Employee(String name, int annualCost, String id) {
        super(name);
        this.annualCost = annualCost;
        this.id = id;
    }

    @Override
    public int getAnnualCost() {
        return annualCost;
    }

    public String getId() {
        return id;
    }
}
