package ru.eshkin.sd.refactoring;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import ru.eshkin.sd.refactoring.dao.ProductDao;
import ru.eshkin.sd.refactoring.servlet.AddProductServlet;
import ru.eshkin.sd.refactoring.servlet.GetProductsServlet;
import ru.eshkin.sd.refactoring.servlet.QueryServlet;

public class Main {
    private static final String CONNECTION_URL = "jdbc:sqlite:main.db";

    public static void main(String[] args) throws Exception {
        ProductDao productDao = new ProductDao(CONNECTION_URL);
        Server server = new Server(8081);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        server.setHandler(context);

        context.addServlet(new ServletHolder(new AddProductServlet(productDao)), "/add-product");
        context.addServlet(new ServletHolder(new GetProductsServlet(productDao)),"/get-products");
        context.addServlet(new ServletHolder(new QueryServlet(productDao)),"/query");

        server.start();
        server.join();
    }
}
