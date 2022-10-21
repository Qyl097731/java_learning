package com.nju.refactor.chap11.demo2;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Vector;

/**
 * @description
 * @date:2022/10/21 12:51
 * @author: qyl
 */
public class Department extends Party{
    private List<Employee> staff = new ArrayList<>();

    public Department(String name) {
        super(name);
    }

    @Override
    public int getAnnualCost(){
        return staff.stream()
                .map(Employee::getAnnualCost)
                .reduce(0,Integer::sum);
    }

    public List<Employee> getStaff() {
        return staff;
    }

    public void addStaff(Employee employee){
        staff.add(employee);
    }
}
