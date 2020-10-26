package ru.shchetsova.sd.refactoring.db;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DataBaseImpl implements DataBase{

    @Override
    public void createDB() throws SQLException {
        try (Connection c = DriverManager.getConnection(DB_URL)) {
            String sql = "CREATE TABLE IF NOT EXISTS PRODUCT" +
                    "(ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                    " NAME           TEXT    NOT NULL, " +
                    " PRICE          INT     NOT NULL)";
            Statement stmt = c.createStatement();

            stmt.executeUpdate(sql);
            stmt.close();
        }
    }

    @Override
    public void addProduct(Product product) throws SQLException {
        try (Connection c = DriverManager.getConnection("jdbc:sqlite:test.db")) {
            String sql = "INSERT INTO PRODUCT " +
                    "(NAME, PRICE) VALUES (\"" + product.getName() + "\"," + product.getPrice() + ")";
            Statement stmt = c.createStatement();
            stmt.executeUpdate(sql);
            stmt.close();
        }
    }

    @Override
    public List<Product> getAllProduct() throws SQLException {
        List<Product> products = new ArrayList<>();
        try (Connection c = DriverManager.getConnection("jdbc:sqlite:test.db")) {
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM PRODUCT");
            while (rs.next()) {
                String name = rs.getString("name");
                int price = rs.getInt("price");
                products.add(new Product(name, price));
            }
        }
        return products;
    }

    @Override
    public Product getProductWithMinPrise() throws SQLException {
        return null;
    }

    @Override
    public Product getProductWithMaxPrise() throws SQLException {
        return null;
    }

    @Override
    public long getSumPrise() throws SQLException {
        return 0;
    }

    @Override
    public long getCountOfProduct() throws SQLException {
        return 0;
    }
}
