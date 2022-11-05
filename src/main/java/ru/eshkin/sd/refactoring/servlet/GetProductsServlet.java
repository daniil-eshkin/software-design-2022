package ru.eshkin.sd.refactoring.servlet;

import ru.eshkin.sd.refactoring.dao.ProductDao;
import ru.eshkin.sd.refactoring.model.Product;
import ru.eshkin.sd.refactoring.servlet.http.HttpServletResponseBuilder;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class GetProductsServlet extends HttpServlet {
    private final ProductDao productDao;

    public GetProductsServlet(ProductDao productDao) {
        this.productDao = productDao;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<Product> products = productDao.getAll();

        HttpServletResponseBuilder builder = HttpServletResponseBuilder.htmlBody(response);
        for (Product product : products) {
            builder.line(product.toHtml());
        }
        builder.build();
    }
}
