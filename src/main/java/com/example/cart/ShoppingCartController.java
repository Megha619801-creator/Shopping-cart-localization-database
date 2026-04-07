package com.example.cart;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.*;

public class ShoppingCartController {

    @FXML private ComboBox<String> languageComboBox;
    @FXML private Button confirmLanguageButton;
    @FXML private Label numberOfItemsLabel;
    @FXML private TextField numberOfItemsField;
    @FXML private Button enterItemsButton;
    @FXML private VBox itemsContainer;
    @FXML private Button calculateTotalButton;
    @FXML private Label totalLabel;

    private final ShoppingCartCalculator calculator = new ShoppingCartCalculator();
    private final LocalizationService localizationService = new LocalizationService();
    private final CartService cartService = new CartService();

    private Map<String, String> strings = new HashMap<>();
    private String currentLanguageCode = "en";

    private final Map<String, String> languageDisplayToCode = Map.of(
            "English", "en",
            "Suomi", "fi",
            "Svenska", "sv",
            "日本語", "ja",
            "العربية", "ar"
    );

    @FXML
    public void initialize() {
        languageComboBox.setItems(FXCollections.observableArrayList(languageDisplayToCode.keySet()));
        languageComboBox.getSelectionModel().select("English");

        loadLanguage("en");

        confirmLanguageButton.setOnAction(e -> {
            String selected = languageComboBox.getValue();
            String code = languageDisplayToCode.getOrDefault(selected, "en");
            loadLanguage(code);
        });

        enterItemsButton.setOnAction(e -> createItemInputs());
        calculateTotalButton.setOnAction(e -> calculateAndSave());
    }

    private void loadLanguage(String languageCode) {
        this.currentLanguageCode = languageCode;
        this.strings = localizationService.loadStrings(languageCode);
        updateTexts();
    }

    private void updateTexts() {
        numberOfItemsLabel.setText(strings.getOrDefault("label.numberOfItems", "Number of items:"));
        enterItemsButton.setText(strings.getOrDefault("button.enterItems", "Enter Items"));
        calculateTotalButton.setText(strings.getOrDefault("button.calculateTotal", "Calculate Total"));
        confirmLanguageButton.setText(strings.getOrDefault("button.confirmLanguage", "Confirm Language"));
        totalLabel.setText(strings.getOrDefault("label.total", "Total:") + " 0");
    }

    private void createItemInputs() {
        itemsContainer.getChildren().clear();

        int count;
        try {
            count = Integer.parseInt(numberOfItemsField.getText());
        } catch (Exception e) {
            showError();
            return;
        }

        for (int i = 0; i < count; i++) {
            HBox row = new HBox(10);

            Label priceLabel = new Label(strings.getOrDefault("label.price", "Price:"));
            TextField priceField = new TextField();

            Label qtyLabel = new Label(strings.getOrDefault("label.quantity", "Quantity:"));
            TextField qtyField = new TextField();

            row.getChildren().addAll(priceLabel, priceField, qtyLabel, qtyField);
            row.setUserData(new TextField[]{priceField, qtyField});

            itemsContainer.getChildren().add(row);
        }
    }

    private void calculateAndSave() {
        List<Item> items = new ArrayList<>();

        for (Node node : itemsContainer.getChildren()) {
            TextField[] fields = (TextField[]) node.getUserData();
            try {
                double price = Double.parseDouble(fields[0].getText());
                int qty = Integer.parseInt(fields[1].getText());
                items.add(new Item(price, qty));
            } catch (Exception e) {
                showError();
                return;
            }
        }

        double total = calculator.calculateCartTotal(items);
        totalLabel.setText(strings.getOrDefault("label.total", "Total:") + " " + total);

        // Save to DB
        int cartId = cartService.saveCartRecord(items.size(), total, currentLanguageCode);

        if (cartId > 0) {
            int index = 1;
            for (Item item : items) {
                double subtotal = item.price() * item.quantity();
                cartService.saveCartItem(cartId, index++, item.price(), item.quantity(), subtotal);
            }
        }
    }

    private void showError() {
        Alert alert = new Alert(Alert.AlertType.ERROR, strings.getOrDefault("error.invalidNumber", "Invalid number"));
        alert.showAndWait();
    }
}
