package com.nju.refactor.chap01.movie;

/**
 * @description
 * @date:2022/10/12 15:28
 * @author: qyl
 */
public class RegularPrice extends Price {

    @Override
    public int getPriceCode() {
        return Movie.REGULAR;
    }

    @Override
    public double getCharge(int daysRented) {
        double result = 0;
        result += 2;
        if (daysRented >= 2){
            result += (daysRented - 2) * 1.5;
        }
        return result;
    }
}
