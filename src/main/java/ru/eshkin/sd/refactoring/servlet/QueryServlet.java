package ru.eshkin.sd.refactoring.servlet;

import ru.eshkin.sd.refactoring.dao.ProductDao;
import ru.eshkin.sd.refactoring.servlet.http.HttpServletResponseBuilder;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class QueryServlet extends HttpServlet {
    private final ProductDao productDao;

    public QueryServlet(ProductDao productDao) {
        this.productDao = productDao;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String command = request.getParameter("command");

        if ("max".equals(command)) {
            HttpServletResponseBuilder.htmlBody(response)
                    .header("Product with max price: ")
                    .line(productDao.getProductWithMaxPrice().toHtml())
                    .build();
        } else if ("min".equals(command)) {
            HttpServletResponseBuilder.htmlBody(response)
                    .header("Product with min price: ")
                    .line(productDao.getProductWithMinPrice().toHtml())
                    .build();
        } else if ("sum".equals(command)) {
            HttpServletResponseBuilder.htmlBody(response)
                    .line("Summary price: ")
                    .line(productDao.getSummaryPrice())
                    .build();
        } else if ("count".equals(command)) {
            HttpServletResponseBuilder.htmlBody(response)
                    .line("Number of products: ")
                    .line(productDao.getProductsCount())
                    .build();
        } else {
            HttpServletResponseBuilder.buildFromText(response, "Unknown command: " + command);
        }
    }
}
