package com.example.cart;

import javafx.embed.swing.JFXPanel;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;

class ShoppingCartControllerCoverageTest {

    @BeforeAll
    static void initToolkit() {
        new JFXPanel(); // starts JavaFX runtime
    }

    @Test
    void testInitializeAndCreateInputs() throws Exception {
        ShoppingCartController controller = new ShoppingCartController();

        setField(controller, "languageComboBox", new ComboBox<String>());
        setField(controller, "confirmLanguageButton", new Button());
        setField(controller, "numberOfItemsLabel", new Label());
        setField(controller, "numberOfItemsField", new TextField("2"));
        setField(controller, "enterItemsButton", new Button());
        setField(controller, "itemsContainer", new VBox());
        setField(controller, "calculateTotalButton", new Button());
        setField(controller, "totalLabel", new Label());

        controller.initialize();

        Method method = ShoppingCartController.class
                .getDeclaredMethod("createItemInputs");
        method.setAccessible(true);
        method.invoke(controller);

        VBox box = (VBox) getField(controller, "itemsContainer");
        assertEquals(2, box.getChildren().size());
    }

    @Test
    void testCalculateAndSave() throws Exception {
        ShoppingCartController controller = new ShoppingCartController();

        VBox box = new VBox();

        TextField price = new TextField("10");
        TextField qty = new TextField("2");

        javafx.scene.layout.HBox row = new javafx.scene.layout.HBox();
        row.setUserData(new TextField[]{price, qty});
        box.getChildren().add(row);

        setField(controller, "itemsContainer", box);
        setField(controller, "totalLabel", new Label());

        Method method = ShoppingCartController.class
                .getDeclaredMethod("calculateAndSave");
        method.setAccessible(true);

        assertDoesNotThrow(() -> method.invoke(controller));
    }

    private void setField(Object obj, String fieldName, Object value) throws Exception {
        Field field = obj.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(obj, value);
    }

    private Object getField(Object obj, String fieldName) throws Exception {
        Field field = obj.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        return field.get(obj);
    }
}