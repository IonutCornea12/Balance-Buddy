package com.example.proiect2;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class LoginController {

    @FXML
    private TextField userEmailField;
    @FXML
    private PasswordField userPasswordField;
    @FXML
    private Label statusLabel;

    @FXML
    private void initialize() {
    }

    @FXML
    private void login() {
        String userEmail = this.userEmailField.getText();
        String userPassword = this.userPasswordField.getText();

        // Check if email and password are not empty
        if (userEmail.isEmpty() || userPassword.isEmpty()) {
            this.statusLabel.setText("Please enter both email and password.");
            return;
        }

        this.authenticateUser(userEmail, userPassword);
    }

    @FXML
    private void createAccount() {
        String userEmail = this.userEmailField.getText();
        String userPassword = this.userPasswordField.getText();

        // Check if email and password are not empty
        if (userEmail.isEmpty() || userPassword.isEmpty()) {
            this.statusLabel.setText("Please enter both email and password.");
            return;
        }

        if (DbFunctions.authenticateUser(userEmail, userPassword)) {
            this.statusLabel.setText("Account with this email already exists");
        } else if (DbFunctions.addUser(userEmail, userPassword)) {
            this.statusLabel.setText("Account Created Successfully");
        } else {
            this.statusLabel.setText("Failed to Create Account");
        }
    }

    private void authenticateUser(String userEmail, String userPassword) {
        if (DbFunctions.authenticateUser(userEmail, userPassword)) {
            this.statusLabel.setText("Login Successful");
            this.openAccountingApp(userEmail);
        } else {
            this.statusLabel.setText("Login Failed");
        }
    }

    private void openAccountingApp(String userEmail) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("accounting_app.fxml"));
            loader.setControllerFactory(controller -> {
                AccountingAppController accountingAppController = new AccountingAppController();
                accountingAppController.setUserEmail(userEmail);
                return accountingAppController;
            });

            VBox root = loader.load();
            AccountingAppController controller = loader.getController();

            // Get the current stage (login stage)
            Stage loginStage = (Stage) userEmailField.getScene().getWindow();

            // Create a new stage for the accounting app
            Stage accountingAppStage = new Stage();
            Scene scene = new Scene(root);
            accountingAppStage.setScene(scene);
            accountingAppStage.setTitle("Accounting App");
            accountingAppStage.show();

            // Close the login stage
            loginStage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}