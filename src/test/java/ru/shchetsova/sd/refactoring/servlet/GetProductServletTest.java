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

public class GetProductServletTest {
    private final HttpServletRequest request = mock(HttpServletRequest.class);
    private final HttpServletResponse response = mock(HttpServletResponse.class);
    private final StringWriter writer = new StringWriter();
    private final GetProductsServlet getProductsServlet = new GetProductsServlet();

    private static final String DB_ADDRESS = "jdbc:sqlite:test.db";

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
    public void OK() throws SQLException, IOException {
        try (final var connection = DriverManager.getConnection(DB_ADDRESS)) {
            final var query = """
                    insert into product(name, price) values
                        ('bla', '1'),
                        ('aaa', '0'),
                        ('bb', '-1')
                    """;
            connection.prepareStatement(query).execute();
        }
        getProductsServlet.doGet(request, response);
        assertEquals("""
                <html><body>\r
                bla	1</br>\r
                aaa	0</br>\r
                bb	-1</br>\r
                </body></html>\r
                """, writer.toString());
    }

    @Test
    public void OKEmpty() throws IOException {
        getProductsServlet.doGet(request, response);
        assertEquals("""
                <html><body>\r
                </body></html>\r
                """, writer.toString());
    }
}
