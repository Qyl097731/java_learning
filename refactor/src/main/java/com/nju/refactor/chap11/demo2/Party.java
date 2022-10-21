package com.nju.refactor.chap11.demo2;

/**
 * @description
 * @date:2022/10/21 12:58
 * @author: qyl
 */
abstract class Party {
    private String name;

    protected Party(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    abstract int getAnnualCost();
}
