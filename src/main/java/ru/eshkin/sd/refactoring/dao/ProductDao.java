package ru.eshkin.sd.refactoring.dao;

import ru.eshkin.sd.refactoring.exception.ProductDaoException;
import ru.eshkin.sd.refactoring.model.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class ProductDao {
    private final String CONNECTION_URL;

    public ProductDao(String connection_url) {
        CONNECTION_URL = connection_url;
        executeUpdate("CREATE TABLE IF NOT EXISTS PRODUCT" +
                "(ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                " NAME           TEXT    NOT NULL, " +
                " PRICE          INT     NOT NULL)");
    }

    public void deleteAll() {
        executeUpdate("DELETE FROM PRODUCT WHERE 1 = 1");
    }

    public void addProduct(Product product) {
        executeUpdate("INSERT INTO PRODUCT " +
                "(NAME, PRICE) VALUES (\"" + product.getName() + "\"," + product.getPrice() + ")");
    }

    public List<Product> getAll() {
        return executeQuery("SELECT * FROM PRODUCT",
                this::parseProducts);
    }

    public Product getProductWithMaxPrice() {
        return executeQuery("SELECT * FROM PRODUCT ORDER BY PRICE DESC LIMIT 1",
                rs -> parseProducts(rs).get(0));
    }

    public Product getProductWithMinPrice() {
        return executeQuery("SELECT * FROM PRODUCT ORDER BY PRICE LIMIT 1",
                rs -> parseProducts(rs).get(0));
    }

    public int getSummaryPrice() {
        return executeQuery("SELECT SUM(price) FROM PRODUCT",
                this::parseInt);
    }

    public int getProductsCount() {
        return executeQuery("SELECT COUNT(*) FROM PRODUCT",
                this::parseInt);
    }

    private int parseInt(ResultSet rs) {
        try {
            rs.next();
            return rs.getInt(1);
        } catch (SQLException e) {
            throw new ProductDaoException(e);
        }
    }

    private List<Product> parseProducts(ResultSet rs) {
        List<Product> products = new ArrayList<>();

        try {
            while (rs.next()) {
                products.add(Product.builder()
                        .name(rs.getString("name"))
                        .price(rs.getInt("price"))
                        .build());
            }
        } catch (SQLException e) {
            throw new ProductDaoException(e);
        }

        return products;
    }

    private void executeUpdate(String sql) {
        try (Connection c = DriverManager.getConnection(CONNECTION_URL)) {
            Statement stmt = c.createStatement();

            stmt.executeUpdate(sql);
            stmt.close();
        } catch (SQLException e) {
            throw new ProductDaoException(e);
        }
    }

    private <T> T executeQuery(String sql, Function<ResultSet, T> handler) {
        try (Connection c = DriverManager.getConnection(CONNECTION_URL)) {
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            T res = handler.apply(rs);

            rs.close();
            stmt.close();

            return res;
        } catch (SQLException e) {
            throw new ProductDaoException(e);
        }
    }
}
