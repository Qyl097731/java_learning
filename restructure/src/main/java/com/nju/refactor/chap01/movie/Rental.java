package com.nju.refactor.chap01.movie;

/**
 * @description
 * @date:2022/10/12 16:18
 * @author: qyl
 */
public class Rental {
    private Movie movie;
    private int daysRented;

    public Rental(){}


    public Rental(Movie movie, int daysRented) {
        this.movie = movie;
        this.daysRented = daysRented;
    }

    public double getCharge(){
        return movie.getCharge(daysRented);
    }

    public int getFrequentRenterPoints(){
        return movie.getFrequentRenterPoints(daysRented);
    }
}
