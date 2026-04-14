package com.example.cart;

import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ServiceCoverageTest {

    @Test
    void testDatabaseConnection() {
        assertDoesNotThrow(() -> {
            Connection conn = DatabaseConnection.getConnection();
            assertNotNull(conn);
            assertFalse(conn.isClosed());
            conn.close();
        });
    }

    @Test
    void testSaveCartRecord() {
        CartService service = new CartService();

        int id = service.saveCartRecord(2, 50.0, "en");

        assertTrue(id > 0);
    }

    @Test
    void testSaveCartItem() {
        CartService service = new CartService();

        int cartId = service.saveCartRecord(1, 10.0, "en");

        assertDoesNotThrow(() ->
                service.saveCartItem(cartId, 1, 10.0, 1, 10.0)
        );
    }

    @Test
    void testLoadLocalizationStrings() {
        LocalizationService service = new LocalizationService();

        Map<String, String> map = service.loadStrings("en");

        assertNotNull(map);
    }
}