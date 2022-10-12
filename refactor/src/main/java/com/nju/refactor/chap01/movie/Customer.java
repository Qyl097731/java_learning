package com.nju.refactor.chap01.movie;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @description
 * @date:2022/10/12 16:14
 * @author: qyl
 */
public class Customer {
    private String name;
    private List<Rental> rentals;

    public Customer(){}


    public Customer(String name, List<Rental> rentals) {
        this.name = name;
        this.rentals = rentals;
    }

    public double getTotalCharge(){
         return rentals.stream().map(Rental::getCharge).reduce(0.0, Double::sum);
    }

    public int getFrequentRentalPoints(){
        return rentals.stream().map(Rental::getFrequentRenterPoints).reduce(0,Integer::sum);
    }

    public static void main(String[] args) {
        List<Rental> rentals = new ArrayList<>();
        Movie movie = new Movie("movie", 2);
        rentals.add(new Rental(movie,10));
        Customer customer = new Customer("test", rentals);
        System.out.println(customer.getTotalCharge());
    }
}
