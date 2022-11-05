package ru.eshkin.sd.refactoring.servlet.http;

import ru.eshkin.sd.refactoring.model.Product;

import javax.servlet.http.HttpServletRequest;

public class HttpServletRequestUtils {
    public static Product getProduct(HttpServletRequest request) {
        return Product.builder()
                .name(request.getParameter("name"))
                .price(Integer.parseInt(request.getParameter("price")))
                .build();
    }
}
