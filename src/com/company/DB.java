package com.company;

import java.sql.*;

public class DB {

    private final String HOST = "localhost";
    private final String PORT = "3307";
    private final String DB_NAME = "module4";
    private final String LOGIN = "root";
    private final String PASS = "root";

    private Connection dbConn = null;

    private Connection getDBConnection() throws ClassNotFoundException, SQLException {

        String connStr = "jdbc:mysql://" + HOST + ":" + PORT + "/" + DB_NAME;
        Class.forName("com.mysql.cj.jdbc.Driver");

        dbConn = DriverManager.getConnection(connStr, LOGIN, PASS);

        return dbConn;
    }

    public void isConnected() throws ClassNotFoundException, SQLException {

        dbConn = getDBConnection();
        System.out.println(dbConn.isValid(1000));
    }

    public void createTableItems(String tableName) throws ClassNotFoundException, SQLException {

        String sql = "CREATE TABLE IF NOT EXISTS " + tableName
                + " (id INT AUTO_INCREMENT PRIMARY KEY, title VARCHAR(50), price INT, category VARCHAR(50))"
                + " ENGINE=MYISAM;";

        Statement statement = getDBConnection().createStatement();
        statement.executeUpdate(sql);
    }

    public void createTableUsers(String tableName) throws ClassNotFoundException, SQLException {

        String sql = "CREATE TABLE IF NOT EXISTS " + tableName
                + " (id INT AUTO_INCREMENT PRIMARY KEY, login VARCHAR(50), password VARCHAR(100))"
                + " ENGINE=MYISAM;";

        Statement statement = getDBConnection().createStatement();
        statement.executeUpdate(sql);
    }

    public void createTableOrders(String tableName) throws ClassNotFoundException, SQLException {

        String sql = "CREATE TABLE IF NOT EXISTS " + tableName
                + " (id INT AUTO_INCREMENT PRIMARY KEY, user_id INT, item_id INT)"
                + " ENGINE=MYISAM;";

        Statement statement = getDBConnection().createStatement();
        statement.executeUpdate(sql);
    }

    public void insertArticleUsers(String login, String password) throws ClassNotFoundException, SQLException {

        String sql = "INSERT INTO `users` (login, password) VALUES (?, ?)";

        PreparedStatement prSt = getDBConnection().prepareStatement(sql);
        prSt.setString(1, login);
        prSt.setString(2, password);

        prSt.executeUpdate();
    }

    public void insertArticleItems(String title, int price, String category) throws ClassNotFoundException, SQLException {

        String sql = "INSERT INTO `items` (title, price, category) VALUES (?, ?, ?)";
        PreparedStatement prSt = getDBConnection().prepareStatement(sql);
        prSt.setString(1, title);
        prSt.setInt(2, price);
        prSt.setString(3, category);

        prSt.executeUpdate();
    }

    public void insertArticleOrders(int user_id, int item_id) throws ClassNotFoundException, SQLException {

        String sql = "INSERT INTO `orders` (user_id, items_id) VALUES (?, ?)";
        PreparedStatement prSt = getDBConnection().prepareStatement(sql);
        prSt.setInt(1, user_id);
        prSt.setInt(2, item_id);

        prSt.executeUpdate();
    }

    public void setOrders(int user_id, int item_id) throws ClassNotFoundException, SQLException {

        String sql = "INSERT INTO `orders` (user_id, item_id) VALUES (?, ?)";
        PreparedStatement prSt = getDBConnection().prepareStatement(sql);
        prSt.setInt(1, user_id);
        prSt.setInt(2, item_id);

        prSt.executeUpdate();
    }

    public void getOrderInfo() throws ClassNotFoundException, SQLException {

        String sql = "SELECT * FROM `orders`";
        Statement statement = getDBConnection().createStatement();
        ResultSet res = statement.executeQuery(sql);

        System.out.println("Список заказов: " + "\n");

        while(res.next()) {

            String user_id_name = "SELECT * FROM `users`";
            Statement statementUser = getDBConnection().createStatement();
            ResultSet resUser = statementUser.executeQuery(user_id_name);

            String item_id_title = "SELECT * FROM `items`";
            Statement statementItem = getDBConnection().createStatement();
            ResultSet resItem = statementItem.executeQuery(item_id_title);

            String order = null;
            String userName = null;
            String productTitle = null;

            while (resUser.next()) {
                if (res.getInt("user_id") == resUser.getInt("id")) {
                    userName = resUser.getString("login");
                }
            }

            while (resItem.next()) {
                if (res.getInt("item_id") == resItem.getInt("id")) {
                    productTitle = resItem.getString("title");
                }
            }

            order = userName + " - " + productTitle;

            System.out.println(order);
        }
    }
}
