package com.example.nikhil.aircanteen_customer;


import android.media.Image;

/**
 * Created by nikhil on 29/3/16.
 */
public class Item {

    int id;
    String name;
    double price;
    int time;
    boolean available;
    String foodType;
    int image;
    int quantity;

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public Item(long id, String name, double price, int time, boolean available, String foodType, int image) {
        this.id = (int)id;
        this.name = name;
        this.price = price;
        this.time = time;
        this.available = available;
        this.foodType = foodType;
        this.image = image;
        this.quantity = 0;
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", time=" + time +
                ", available=" + available +
                ", foodType='" + foodType + '\'' +
                ", image=" + image +
                ", quantity=" + quantity +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFoodType() {
        return foodType;
    }

    public void setFoodType(String foodType) {
        this.foodType = foodType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
}

