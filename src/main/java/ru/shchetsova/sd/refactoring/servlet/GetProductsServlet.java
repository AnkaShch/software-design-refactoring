package ru.shchetsova.sd.refactoring.servlet;

import ru.shchetsova.sd.refactoring.db.DataBase;
import ru.shchetsova.sd.refactoring.Product;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 * @author akirakozov
 */
public class GetProductsServlet extends HttpServlet {
    private final DataBase db;

    public GetProductsServlet(DataBase db) {
        this.db = db;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final List<Product> products;
        try {
            products = db.getAllProduct();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        response.getWriter().println("<html><body>");
        for (final Product product : products) {
            response.getWriter().println(product.getName() + "\t" + product.getPrice() + "</br>");
        }
        response.getWriter().println("</body></html>");
        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
