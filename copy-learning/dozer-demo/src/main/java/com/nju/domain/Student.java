package com.nju.domain;

import java.util.Date;

/**
 * @description
 * @date 2023/7/13 23:52
 * @author: qyl
 */
public class Student {
    private String name;
    private int score;
    private Date date;

    private Address address;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
