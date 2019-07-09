package com.uas.rentalin.model;

public class Item {

    private final int id;
    private final String name;
    private final String price;
    private final String image;

    public Item(int id, String name, String price, String image) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public String getImage() {
        return image;
    }
}
