package com.nju.refactor.chap11.demo01;

/**
 * @description
 * @date:2022/10/21 11:48
 * @author: qyl
 */
public class JobItem {
    public JobItem(int unitPrice, int quantity) {
        this.unitPrice = unitPrice;
        this.quantity = quantity;
    }

    public int getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(int unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    protected boolean isLabor() {
        return false;
    }

    private int unitPrice;
    private int quantity;
    protected Employee employee;
}
