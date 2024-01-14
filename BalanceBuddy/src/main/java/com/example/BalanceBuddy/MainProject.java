package com.example.BalanceBuddy;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainProject extends Application {
    public MainProject() {
    }

    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("login.fxml"));
        loader.setControllerFactory((controller) -> {
            return new LoginController();
        });
        Parent root = (Parent)loader.load();
        Scene scene = new Scene(root);

        primaryStage.setScene(scene);
        primaryStage.setTitle("BalanceBuddy");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
