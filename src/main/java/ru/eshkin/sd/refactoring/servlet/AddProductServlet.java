package ru.eshkin.sd.refactoring.servlet;

import ru.eshkin.sd.refactoring.dao.ProductDao;
import ru.eshkin.sd.refactoring.model.Product;

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
        productDao.addProduct(Product.builder()
                        .name(request.getParameter("name"))
                        .price(Integer.parseInt(request.getParameter("price")))
                .build());

        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().println("OK");
    }
}
