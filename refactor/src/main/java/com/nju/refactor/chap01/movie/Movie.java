package com.nju.refactor.chap01.movie;


/**
 * @description 经典电影票计费设计：策略模式 、 状态模式都行 这里采用状态模式
 * @date:2022/10/12 15:10
 * @author: qyl
 */
public class Movie {
    public static final int CHILDRENS = 2;
    public static final int NEW_RELEASE = 1;
    public static final int REGULAR =0;
    private String title;
    private Price price;

    public Movie(String title,Integer priceCode){
        this.title = title;
        setPriceCode(priceCode);
    }

    public int getFrequentRenterPoints(int daysRented){
        return price.getFrequentRenterPoints(daysRented);
    }

    public double getCharge(int daysRented){
        return price.getCharge(daysRented);
    }

    public String getTitle() {
        return title;
    }

    public Price getPrice() {
        return price;
    }

    private void setPriceCode(Integer priceCode) {
        switch (priceCode){
            case REGULAR:
               this.price = new RegularPrice();
                break;
            case NEW_RELEASE:
               this.price = new NewReleasePrice();
                break;
            case CHILDRENS:
               this.price = new ChildrensPrice();
                break;
            default:
                throw new IllegalArgumentException("Incorrect Price Code");
        }
    }
}
