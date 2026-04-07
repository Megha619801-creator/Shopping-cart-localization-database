package com.example.cart;

import java.util.List;

public class ShoppingCartCalculator {

    public double calculateItemTotal(double price, int quantity) {
        return price * quantity;
    }

    public double calculateCartTotal(List<Item> items) {
        return items.stream()
                .mapToDouble(i -> calculateItemTotal(i.price(), i.quantity()))
                .sum();
    }
}
