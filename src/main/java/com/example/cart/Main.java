package com.example.cart;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.SQLException;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/main-view.fxml"));
        Scene scene = new Scene(loader.load());

        stage.setTitle("MEGHA / Shopping Cart App (DB Localization)");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) throws SQLException {
        launch(args);
        System.out.println(DatabaseConnection.getConnection());

    }
}
