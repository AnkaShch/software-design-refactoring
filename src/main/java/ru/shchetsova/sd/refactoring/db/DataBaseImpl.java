package ru.shchetsova.sd.refactoring.db;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DataBaseImpl implements DataBase {

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
        try (Connection c = DriverManager.getConnection(DB_URL)) {
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
        final String request = "SELECT * FROM PRODUCT ORDER BY PRICE LIMIT 1";
        return getProductByRequest(request);
    }

    @Override
    public Product getProductWithMaxPrise() throws SQLException {
        final String request = "SELECT * FROM PRODUCT ORDER BY PRICE DESC LIMIT 1";
        return getProductByRequest(request);
    }

    @Override
    public long getSumPrise() throws SQLException {
        final String request = "SELECT SUM(price) FROM PRODUCT";
        return getIntByRequest(request);
    }

    @Override
    public long getCountOfProduct() throws SQLException {
        final String request = "SELECT COUNT(*) FROM PRODUCT";
        return getIntByRequest(request);
    }

    private Product getProductByRequest(final String request) throws SQLException {
        try (Connection c = DriverManager.getConnection(DB_URL)) {
            try (Statement stmt = c.createStatement()) {
                final ResultSet rs = stmt.executeQuery(request);
                if (rs.next()) {
                    final String name = rs.getString("name");
                    final int price = rs.getInt("price");
                    return new Product(name, price);
                } else {
                    return null;
                }
            }
        }
    }

    private long getIntByRequest(final String request) throws SQLException {
        try (Connection c = DriverManager.getConnection(DB_URL)) {
            Statement stmt = c.createStatement();
            final ResultSet rs = stmt.executeQuery(request);
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        return 0;
    }
}
