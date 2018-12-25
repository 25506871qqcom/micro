package net.xdclass.product_service.domain;

import java.io.Serializable;

public class Product implements Serializable {

    private int id ;
    private String name ;
    private int price ;
    private int score ;


    public Product(int id, String name, int price, int score) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.score = score;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
