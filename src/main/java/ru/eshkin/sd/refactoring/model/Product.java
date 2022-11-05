package ru.eshkin.sd.refactoring.model;

public class Product {
    private final String name;

    private final int price;

    public Product(String name, int price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public String toHtml() {
        return name + "\t" + price + "</br>";
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String name;
        private int price;

        public Product build() {
            return new Product(name, price);
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder price(int price) {
            this.price = price;
            return this;
        }
    }
}
