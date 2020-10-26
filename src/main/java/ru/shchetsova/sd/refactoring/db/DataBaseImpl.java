package ru.shchetsova.sd.refactoring.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

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
    public void getAllProduct() throws SQLException {

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
