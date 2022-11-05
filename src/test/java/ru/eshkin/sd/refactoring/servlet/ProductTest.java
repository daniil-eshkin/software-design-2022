package ru.eshkin.sd.refactoring.servlet;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;
import ru.eshkin.sd.refactoring.dao.ProductDao;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collections;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ProductTest {
    private static final String CONNECTION_URL = "jdbc:sqlite:test.db";

    private final ProductDao productDao = new ProductDao(CONNECTION_URL);
    private final GetProductsServlet getProductsServlet = new GetProductsServlet(productDao);
    private final AddProductServlet addProductServlet = new AddProductServlet(productDao);
    private final QueryServlet queryServlet = new QueryServlet(productDao);

    @After
    public void after() {
        productDao.deleteAll();
    }

    @Test
    public void getProductsEmpty() {
        assertGetProducts("<html><body>\n" +
                "</body></html>\n");
    }

    @Test
    public void addProduct() {
        assertAddProduct("traktor", "300");
        assertGetProducts("<html><body>\n" +
                        "traktor\t300</br>\n" +
                "</body></html>\n");
    }

    @Test
    public void addExisting() {
        assertAddProduct("test", "1");
        assertAddProduct("test", "1");
        assertGetProducts("<html><body>\n" +
                "test\t1</br>\n" +
                "test\t1</br>\n" +
                "</body></html>\n");
    }

    @Test
    public void max() {
        fillTable();
        assertQuery("max",
                "<html><body>\n" +
                        "<h1>Product with max price: </h1>\n" +
                        "test3\t3</br>\n" +
                        "</body></html>\n");
    }

    @Test
    public void min() {
        fillTable();
        assertQuery("min",
                "<html><body>\n" +
                        "<h1>Product with min price: </h1>\n" +
                        "test1\t1</br>\n" +
                        "</body></html>\n");
    }

    @Test
    public void sum() {
        fillTable();
        assertQuery("sum",
                "<html><body>\n" +
                        "Summary price: \n" +
                        "6\n" +
                        "</body></html>\n");
    }

    @Test
    public void count() {
        fillTable();
        assertQuery("count",
                "<html><body>\n" +
                        "Number of products: \n" +
                        "3\n" +
                        "</body></html>\n");
    }

    private void fillTable() {
        assertAddProduct("test1", "1");
        assertAddProduct("test2", "2");
        assertAddProduct("test3", "3");

        assertGetProducts("<html><body>\n" +
                "test1\t1</br>\n" +
                "test2\t2</br>\n" +
                "test3\t3</br>\n" +
                "</body></html>\n");
    }

    private static void executeUpdate(String sql) {
        try (Connection c = DriverManager.getConnection(CONNECTION_URL)) {
            Statement stmt = c.createStatement();

            stmt.executeUpdate(sql);
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private HttpServletRequest getMockRequest(Map<String, String> parameters) {
        HttpServletRequest request = mock(HttpServletRequest.class);
        for (Map.Entry<String, String> e : parameters.entrySet()) {
            when(request.getParameter(e.getKey())).thenReturn(e.getValue());
        }

        return request;
    }

    private void assertGetProducts(String responseString) {
        HttpServletResponse response = mock(HttpServletResponse.class);
        StringWriter stringWriter = new StringWriter();

        try (PrintWriter printWriter = new PrintWriter(stringWriter)) {
            when(response.getWriter()).thenReturn(printWriter);
            getProductsServlet.doGet(getMockRequest(Collections.emptyMap()), response);
            assertThat(stringWriter).hasToString(responseString);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void assertAddProduct(String name, String price) {
        HttpServletResponse response = mock(HttpServletResponse.class);
        StringWriter stringWriter = new StringWriter();

        try (PrintWriter printWriter = new PrintWriter(stringWriter)) {
            when(response.getWriter()).thenReturn(printWriter);
            addProductServlet.doGet(getMockRequest(Map.of(
                    "name", name,
                    "price", price
            )), response);
            assertThat(stringWriter).hasToString("OK\n");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void assertQuery(String command, String responseString) {
        HttpServletResponse response = mock(HttpServletResponse.class);
        StringWriter stringWriter = new StringWriter();

        try (PrintWriter printWriter = new PrintWriter(stringWriter)) {
            when(response.getWriter()).thenReturn(printWriter);
            queryServlet.doGet(getMockRequest(Map.of(
                    "command", command
            )), response);
            assertThat(stringWriter).hasToString(responseString);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
