package com.example.nikhil.aircanteen_customer;

import java.util.ArrayList;
import java.util.*;

/**
 * Created by nikhil on 29/3/16.
 */
public class Order {
    ArrayList<Item> items;
    String orderTime;
    String pickupTime;
    String type;
    String orderID;

    public Order(String orderID,ArrayList<Item> items, String orderTime, String pickupTime, String type) {

        this.items = items;
        this.orderTime = orderTime;
        this.type = type;
        this.orderID = orderID;
        this.pickupTime = pickupTime;
    }

    @Override
    public String toString() {
        return "Order{" +
                "items=" + items +
                ", orderTime='" + orderTime + '\'' +
                ", pickupTime='" + pickupTime + '\'' +
                ", type='" + type + '\'' +
                ", orderID='" + orderID + '\'' +
                '}';
    }
}
