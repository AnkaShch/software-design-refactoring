package ru.shchetsova.sd.refactoring.db;

import ru.shchetsova.sd.refactoring.Product;

import java.sql.SQLException;
import java.util.List;

public interface DataBase {
    final String DB_URL = "jdbc:sqlite:test.db";

    void createDB() throws SQLException;

    void addProduct(Product product) throws SQLException;

    List<Product> getAllProduct() throws SQLException;

    Product getProductWithMinPrise() throws SQLException;

    Product getProductWithMaxPrise() throws SQLException;

    long getSumPrise() throws SQLException;

    long getCountOfProduct() throws SQLException;
}
