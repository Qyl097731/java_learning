package com.nju.refactor.chap01.movie;

/**
 * @description
 * @date:2022/10/12 15:20
 * @author: qyl
 */
public class ChildrensPrice extends Price{

    @Override
    public int getPriceCode() {
        return Movie.CHILDRENS;
    }

    @Override
    public double getCharge(int daysRented) {
        double result = 0;
        result += 1.5;
        if (daysRented > 3){
            result += (daysRented-3)*1.5;
        }
        return result;
    }

    @Override
    public int getFrequentRenterPoints(int daysRented) {
        return 0;
    }
}
