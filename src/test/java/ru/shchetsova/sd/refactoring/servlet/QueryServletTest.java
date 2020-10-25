package ru.shchetsova.sd.refactoring.servlet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class QueryServletTest {
    private final HttpServletRequest request = mock(HttpServletRequest.class);
    private final HttpServletResponse response = mock(HttpServletResponse.class);
    private final StringWriter writer = new StringWriter();
    private final QueryServlet queryServlet = new QueryServlet();

    private static final String DB_ADDRESS = "jdbc:sqlite:test.db";
    private static final String DB = """
                    insert into product(name, price) values
                        ('bla', '1'),
                        ('aaa', '0'),
                        ('bb', '-1')
                    """;

    private String joinResult(String res) {
        return "<html><body>\r\n" + res + "</body></html>\r\n";

    }

    @BeforeEach
    public void setup() throws IOException, SQLException {
        when(response.getWriter()).thenReturn(new PrintWriter(writer));
        try (final var connection = DriverManager.getConnection(DB_ADDRESS)) {
            final var query = "drop table if exists product";
            connection.prepareStatement(query).execute();
        }

        try (final var connection = DriverManager.getConnection(DB_ADDRESS)) {
            final var query = """
                    create table if not exists product(
                        id integer primary key autoincrement not null,
                        name text not null,
                        price int not null
                    )
                    """;
            connection.prepareStatement(query).execute();
        }
    }

    @Test
    public void testMax() throws IOException, SQLException {
        when(request.getParameter("command")).thenReturn("max");
        try (final var connection = DriverManager.getConnection(DB_ADDRESS)) {
            connection.prepareStatement(DB).execute();
        }
        queryServlet.doGet(request, response);
        assertEquals(joinResult("<h1>Product with max price: </h1>\r\nbla	1</br>\r\n"), writer.toString());
    }

    @Test
    public void testMin() throws IOException, SQLException {
        when(request.getParameter("command")).thenReturn("min");
        try (final var connection = DriverManager.getConnection(DB_ADDRESS)) {
            connection.prepareStatement(DB).execute();
        }
        queryServlet.doGet(request, response);
        assertEquals(joinResult("<h1>Product with min price: </h1>\r\nbb	-1</br>\r\n"), writer.toString());
    }

    @Test
    public void testSum() throws IOException, SQLException {
        when(request.getParameter("command")).thenReturn("sum");
        try (final var connection = DriverManager.getConnection(DB_ADDRESS)) {
            connection.prepareStatement(DB).execute();
        }
        queryServlet.doGet(request, response);
        assertEquals(joinResult("Summary price:\s\r\n0\r\n"), writer.toString());
    }

    @Test
    public void testCount() throws IOException, SQLException {
        when(request.getParameter("command")).thenReturn("count");
        try (final var connection = DriverManager.getConnection(DB_ADDRESS)) {
            connection.prepareStatement(DB).execute();
        }
        queryServlet.doGet(request, response);
        assertEquals(joinResult("Number of products:\s\r\n3\r\n"), writer.toString());
    }

    @Test
    public void testQueryUnknownCommand() throws IOException {
        when(request.getParameter("command")).thenReturn("unknown");
        queryServlet.doGet(request, response);
        assertEquals("Unknown command: unknown\r\n", writer.toString());
    }

    @Test
    public void testQueryNullCommand() throws IOException {
        queryServlet.doGet(request, response);
        assertEquals("Unknown command: null\r\n", writer.toString());
    }
}