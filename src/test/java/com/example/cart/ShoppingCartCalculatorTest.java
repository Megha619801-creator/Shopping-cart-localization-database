package com.example.cart;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

// ✅ Fix 1: Removed 'public' modifier (not needed in JUnit 5)
// ✅ Fix 2: Removed commented-out SQL line
class ShoppingCartCalculatorTest {

    private ShoppingCartCalculator calc;

    @BeforeEach
    void setUp() {
        calc = new ShoppingCartCalculator();
    }

    // ─── calculateItemTotal ───────────────────────────────────────

    @Test
    void testCalculateItemTotal_normalValues() {
        assertEquals(30.0, calc.calculateItemTotal(10.0, 3));
    }

    @Test
    void testCalculateItemTotal_zeroQuantity() {
        assertEquals(0.0, calc.calculateItemTotal(10.0, 0));
    }

    @Test
    void testCalculateItemTotal_zeroPrice() {
        assertEquals(0.0, calc.calculateItemTotal(0.0, 5));
    }

    @Test
    void testCalculateItemTotal_singleItem() {
        assertEquals(9.99, calc.calculateItemTotal(9.99, 1), 0.001);
    }

    @ParameterizedTest
    @CsvSource({
            "10.0, 3, 30.0",
            "5.5,  4, 22.0",
            "0.0,  5,  0.0",
            "99.9, 1, 99.9"
    })
    void testCalculateItemTotal_parameterized(double price, int qty, double expected) {
        assertEquals(expected, calc.calculateItemTotal(price, qty), 0.001);
    }

    // ─── calculateCartTotal ───────────────────────────────────────

    @Test
    void testCalculateCartTotal_multipleItems() {
        List<Item> items = List.of(
                new Item(10.0, 2),
                new Item(5.5, 3)
        );
        assertEquals(36.5, calc.calculateCartTotal(items), 0.001);
    }

    @Test
    void testCalculateCartTotal_singleItem() {
        List<Item> items = List.of(new Item(7.0, 3));
        assertEquals(21.0, calc.calculateCartTotal(items), 0.001);
    }

    @Test
    void testCalculateCartTotal_emptyCart() {
        assertEquals(0.0, calc.calculateCartTotal(Collections.emptyList()), 0.001);
    }

    @Test
    void testCalculateCartTotal_zeroQuantityItem() {
        List<Item> items = List.of(new Item(10.0, 0));
        assertEquals(0.0, calc.calculateCartTotal(items), 0.001);
    }

    @Test
    void testCalculateCartTotal_allZeroItems() {
        List<Item> items = List.of(
                new Item(0.0, 0),
                new Item(0.0, 0)
        );
        assertEquals(0.0, calc.calculateCartTotal(items), 0.001);
    }

    // ─── Item model ───────────────────────────────────────────────

    @Test
    void testItem_getPrice() {
        Item item = new Item(12.5, 4);
        assertEquals(12.5, item.getPrice(), 0.001);
    }

    @Test
    void testItem_getQuantity() {
        Item item = new Item(12.5, 4);
        assertEquals(4, item.getQuantity());
    }

    @Test
    void testItem_zeroPriceZeroQty() {
        Item item = new Item(0.0, 0);
        assertEquals(0.0, item.getPrice(), 0.001);
        assertEquals(0, item.getQuantity());
    }

    // ─── DatabaseConnection private constructor ───────────────────


    @Test
    void testDatabaseConnection_privateConstructorThrows() {
        assertThrows(UnsupportedOperationException.class,
                ShoppingCartCalculatorTest::invokePrivateConstructor);
    }

    private static void invokePrivateConstructor() throws Exception {
        var constructor = DatabaseConnection.class.getDeclaredConstructor();
        constructor.setAccessible(true);
        try {
            constructor.newInstance();
        } catch (java.lang.reflect.InvocationTargetException e) {
            throw (UnsupportedOperationException) e.getCause();
        }
    }
    }
