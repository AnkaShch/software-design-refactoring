package ru.shchetsova.sd.refactoring.servlet;

import ru.shchetsova.sd.refactoring.Utils;
import ru.shchetsova.sd.refactoring.db.DataBase;
import ru.shchetsova.sd.refactoring.Product;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;

/**
 * @author akirakozov
 */
public class QueryServlet extends HttpServlet {
    private final DataBase db;

    public QueryServlet(DataBase db) {
        this.db = db;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String command = request.getParameter("command");
        final Product product;

        if ("max".equals(command)) {
            try {
                product = db.getProductWithMaxPrise();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            Utils.printHTML(response, "<h1>Product with max price: </h1>", product.getName() + "\t" + product.getPrice() + "</br>");

        } else if ("min".equals(command)) {
            try {
                product = db.getProductWithMinPrise();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            Utils.printHTML(response, "<h1>Product with min price: </h1>", product.getName() + "\t" + product.getPrice() + "</br>");

        } else if ("sum".equals(command)) {
            long sum = 0;
            try {
                sum = db.getSumPrise();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            Utils.printHTML(response, "Summary price: ", String.valueOf(sum));

        } else if ("count".equals(command)) {
            long cnt = 0;
            try {
                cnt = db.getCountOfProduct();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            Utils.printHTML(response, "Number of products: ", String.valueOf(cnt));
        } else {
            response.getWriter().println("Unknown command: " + command);
        }

        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
    }

}
