package com.nju.refactor.chap01.movie;

import java.math.BigDecimal;

/**
 * @description
 * @date:2022/10/12 15:20
 * @author: qyl
 */
public abstract class Price{
    public abstract int getPriceCode();

    public abstract double getCharge(int daysRented);

    public int getFrequentRenterPoints(int daysRented){
        return 1;
    }
}
