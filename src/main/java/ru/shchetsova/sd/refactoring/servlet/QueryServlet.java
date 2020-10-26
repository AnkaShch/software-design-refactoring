package ru.shchetsova.sd.refactoring.servlet;

import ru.shchetsova.sd.refactoring.db.DataBase;
import ru.shchetsova.sd.refactoring.db.Product;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;
import java.util.List;

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
            response.getWriter().println("<html><body>");
            response.getWriter().println("<h1>Product with max price: </h1>");
            response.getWriter().println(product.getName() + "\t" + product.getPrice() + "</br>");
            response.getWriter().println("</body></html>");

        } else if ("min".equals(command)) {
            try {
                product = db.getProductWithMinPrise();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            response.getWriter().println("<h1>Product with max price: </h1>");
            response.getWriter().println(product.getName() + "\t" + product.getPrice() + "</br>");
            response.getWriter().println("</body></html>");
        } else if ("sum".equals(command)) {
            long sum = 0;
            try {
                sum = db.getSumPrise();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            response.getWriter().println("<html><body>");
            response.getWriter().println("Summary price: ");
                response.getWriter().println(sum);
            response.getWriter().println("</body></html>");

        } else if ("count".equals(command)) {
            long cnt = 0;
            try {
                cnt = db.getSumPrise();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            response.getWriter().println("<html><body>");
            response.getWriter().println("Number of products: ");
            response.getWriter().println(cnt);
            response.getWriter().println("</body></html>");
        } else {
            response.getWriter().println("Unknown command: " + command);
        }

        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
    }

}
