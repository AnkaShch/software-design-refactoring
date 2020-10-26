package ru.shchetsova.sd.refactoring;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import ru.shchetsova.sd.refactoring.db.DataBase;
import ru.shchetsova.sd.refactoring.db.DataBaseImpl;
import ru.shchetsova.sd.refactoring.servlet.AddProductServlet;
import ru.shchetsova.sd.refactoring.servlet.GetProductsServlet;
import ru.shchetsova.sd.refactoring.servlet.QueryServlet;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

/**
 * @author akirakozov
 */
public class Main {
    public static void main(String[] args) throws Exception {
        final DataBase db = new DataBaseImpl();

        db.createDB();

        final Server server = new Server(8082);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        server.setHandler(context);

        context.addServlet(new ServletHolder(new AddProductServlet(db)),"/add-product");
        context.addServlet(new ServletHolder(new GetProductsServlet(db)),"/get-products");
        context.addServlet(new ServletHolder(new QueryServlet(db)),"/query");

        server.start();
        server.join();
    }
}
