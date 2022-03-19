package com.company;

import java.sql.SQLException;

public class Main {

    public static void main(String[] args) {

        DB db = new DB();

        try {
            db.isConnected();

            db.createTableItems("items");
            db.createTableUsers("users");
            db.createTableOrders("orders");

            db.insertArticleUsers("john", "pass1");
            db.insertArticleUsers("alex", "pass2");

            db.insertArticleItems("Male cup", 300, "cups");
            db.insertArticleItems("Hat : red", 150, "hats");
            db.insertArticleItems("Hat : blue", 200, "hats");
            db.insertArticleItems("Female cup", 400, "cups");
            db.insertArticleItems("Shirt : red", 550, "shirts");
            db.insertArticleItems("Shirt : Rick and Morty", 700, "shirts");

            db.setOrders(1, 2);
            db.setOrders(1, 3);

            db.getOrderInfo();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
}
