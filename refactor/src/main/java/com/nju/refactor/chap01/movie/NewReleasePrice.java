package com.nju.refactor.chap01.movie;

/**
 * @description
 * @date:2022/10/12 15:27
 * @author: qyl
 */
public class NewReleasePrice extends Price {

    @Override
    public int getPriceCode() {
        return Movie.NEW_RELEASE;
    }

    @Override
    public double getCharge(int daysRented) {
        double result = 0;
        result += daysRented *3;
        return result;
    }

    @Override
    public int getFrequentRenterPoints(int daysRented) {
        return (daysRented > 1) ? 2:1;
    }
}
