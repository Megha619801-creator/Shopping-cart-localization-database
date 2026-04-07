package com.example.cart;

import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class ShoppingCartCalculatorTest {
//    ALTER USER postgres WITH PASSWORD 'study123';

    @Test
    void testCalculateItemTotal() {
        ShoppingCartCalculator calc = new ShoppingCartCalculator();
        assertEquals(30.0, calc.calculateItemTotal(10.0, 3));
    }

    @Test
    void testCalculateCartTotal() {
        ShoppingCartCalculator calc = new ShoppingCartCalculator();
        List<Item> items = List.of(
                new Item(10.0, 2),
                new Item(5.5, 3)
        );
        assertEquals(10*2 + 5.5*3, calc.calculateCartTotal(items));
    }
}
