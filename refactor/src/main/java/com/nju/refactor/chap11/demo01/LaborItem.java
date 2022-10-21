package com.nju.refactor.chap11.demo01;

/**
 * @description
 * @date:2022/10/21 11:52
 * @author: qyl
 */
public class LaborItem extends JobItem {
    public LaborItem(int quantity ,Employee employee) {
        super(0,quantity);
        this.employee = employee;
    }

    @Override
    protected boolean isLabor() {
        return true;
    }

    @Override
    public int getUnitPrice() {
        return employee.getRate();
    }
}
