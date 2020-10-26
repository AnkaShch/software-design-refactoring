package ru.shchetsova.sd.refactoring.db;

import java.sql.SQLException;

public interface DataBase {
    final String DB_URL = "jdbc:sqlite:test.db";

    void createDB() throws SQLException;

    void addProduct(Product product) throws SQLException;

    void getAllProduct() throws SQLException;

    Product getProductWithMinPrise() throws SQLException;

    Product getProductWithMaxPrise() throws SQLException;

    long getSumPrise() throws SQLException;

    long getCountOfProduct() throws SQLException;
}
