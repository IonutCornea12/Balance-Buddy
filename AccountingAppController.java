package com.example.proiect2;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AccountingAppController implements Initializable {

    @FXML
    private TableView<Transaction> transactionTable;
    @FXML
    private TableColumn<Transaction, Date> dateColumn;
    @FXML
    private TableColumn<Transaction, Integer> idColumn;
    @FXML
    private TableColumn<Transaction, String> descriptionColumn;
    @FXML
    private TableColumn<Transaction, Double> amountColumn;
    @FXML
    private TableColumn<Transaction, String> measureUnitColumn;

    @FXML
    private TextField dateTextField;
    @FXML
    private TextField descriptionTextField;
    @FXML
    private TextField amountTextField;
    @FXML
    private TextField measureUnitTextField;
    @FXML
    private Button addTransactionButton;
    @FXML
    private Button deleteTransactionButton;
    @FXML
    private Label userEmailLabel;
    @FXML
    private Button refreshButton;
    private String userEmail;

    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.idColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
        this.amountColumn.setCellValueFactory(cellData -> cellData.getValue().amountProperty().asObject());
        this.dateColumn.setCellValueFactory(cellData -> cellData.getValue().dateProperty());
        this.descriptionColumn.setCellValueFactory(cellData -> cellData.getValue().descriptionProperty());
        this.measureUnitColumn.setCellValueFactory(cellData -> cellData.getValue().measureUnitProperty());

        this.addTransactionButton.setOnAction(event -> this.addTransaction());
        this.userEmailLabel.setText("Welcome to the accounting app: " + this.userEmail);
        this.loadDataFromDatabase();
        this.deleteTransactionButton.setOnAction(event -> this.deleteTransaction());
        this.refreshButton.setOnAction(event -> this.refreshDataFromDatabase());
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
    @FXML
    private void deleteTransaction() {
        Transaction selectedTransaction = transactionTable.getSelectionModel().getSelectedItem();
        if (selectedTransaction != null) {
            boolean success = DbFunctions.deleteTransaction(selectedTransaction.getId());
            if (success) {
                transactionTable.getItems().remove(selectedTransaction);
                System.out.println("Transaction deleted successfully");
            } else {
                System.err.println("Failed to delete transaction from the database");
            }
        } else {
            System.err.println("No transaction selected for deletion");
        }
    }
    private void refreshDataFromDatabase() {
        // Clear existing data
        this.transactionTable.getItems().clear();

        // Reload data from the database
        loadDataFromDatabase();
    }
    private void loadDataFromDatabase() {
        String query = "SELECT * FROM transactions";

        try {
            Connection con = DbFunctions.connect();

            try {
                PreparedStatement pst = con.prepareStatement(query);

                try {
                    ResultSet resultSet = pst.executeQuery();

                    try {
                        while (resultSet.next()) {
                            int id = resultSet.getInt("id");
                            Date transactionDate = resultSet.getDate("transaction_date");
                            String description = resultSet.getString("description");
                            double amount = resultSet.getDouble("amount");
                            String measureUnit = resultSet.getString("measure_unit");
                            Transaction transaction = new Transaction(id, transactionDate, description, amount, measureUnit);

                            Platform.runLater(() ->
                                    this.transactionTable.getItems().add(transaction)
                            );
                        }
                    } catch (Throwable var14) {
                        if (resultSet != null) {
                            try {
                                resultSet.close();
                            } catch (Throwable var13) {
                                var14.addSuppressed(var13);
                            }
                        }
                        throw var14;
                    } finally {
                        if (resultSet != null) {
                            resultSet.close();
                        }
                    }
                } catch (Throwable var15) {
                    if (pst != null) {
                        try {
                            pst.close();
                        } catch (Throwable var12) {
                            var15.addSuppressed(var12);
                        }
                    }
                    throw var15;
                } finally {
                    if (pst != null) {
                        pst.close();
                    }
                }
            } catch (Throwable var16) {
                if (con != null) {
                    try {
                        con.close();
                    } catch (Throwable var11) {
                        var16.addSuppressed(var11);
                    }
                }
                throw var16;
            } finally {
                if (con != null) {
                    con.close();
                }
            }
        } catch (SQLException var17) {
            Logger logger = Logger.getLogger(AccountingAppController.class.getName());
            logger.log(Level.SEVERE, "Error in database operation", var17);
        }
    }

    private void addTransaction() {
        try {
            int id = 0;
            Date date = Date.valueOf(this.dateTextField.getText());
            String description = this.descriptionTextField.getText();
            String measureUnit = this.measureUnitTextField.getText();
            double amount = this.amountTextField.getText().isEmpty() ? 1.0 : Double.parseDouble(this.amountTextField.getText());
            Transaction newTransaction = new Transaction(id, date, description, amount, measureUnit);

            Platform.runLater(() ->
                    this.transactionTable.getItems().add(newTransaction)
            );

            boolean success = DbFunctions.addTransaction(newTransaction);
            if (success) {
                System.out.println("Transaction added successfully");
            } else {
                System.err.println("Failed to add transaction to the database");
            }

            this.dateTextField.clear();
            this.descriptionTextField.clear();
            this.amountTextField.clear();
            this.measureUnitTextField.clear();
        } catch (IllegalArgumentException var8) {
            Logger logger = Logger.getLogger(AccountingAppController.class.getName());
            logger.log(Level.SEVERE, "Error in adding transaction", var8);
        }
    }

}
