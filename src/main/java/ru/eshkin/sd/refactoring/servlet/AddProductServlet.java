package ru.eshkin.sd.refactoring.servlet;

import ru.eshkin.sd.refactoring.dao.ProductDao;
import ru.eshkin.sd.refactoring.servlet.http.HttpServletRequestUtils;
import ru.eshkin.sd.refactoring.servlet.http.HttpServletResponseBuilder;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AddProductServlet extends HttpServlet {
    private final ProductDao productDao;

    public AddProductServlet(ProductDao productDao) {
        this.productDao = productDao;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        productDao.addProduct(HttpServletRequestUtils.getProduct(request));

        HttpServletResponseBuilder.buildFromText(response, "OK");
    }
}
