package com.example.BalanceBuddy;

import javafx.application.Platform;



import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AccountingAppController implements Initializable {
    @FXML
    private TabPane mainTabPane;

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
    private TableColumn<Transaction, String> suppliertranzNameColumn;
    @FXML
    private Button addTransactionButton;
    @FXML
    private Button deleteTransactionButton;
    @FXML
    private Button refreshButton;
    @FXML
    private TextField idTextField;
    @FXML
    private TextField dateTextField;
    @FXML
    private TextField descriptionTextField;
    @FXML
    private TextField amountTextField;
    @FXML
    private TextField measureUnitTextField;
    @FXML
    private TextField suppliertranzNameTextField;


    @FXML
    private TableView<TransactionDetails> transactionDetailsTable;
    @FXML
    private TableColumn<TransactionDetails, Integer> detailsTransactionIdColumn;
    @FXML
    private TableColumn<TransactionDetails, Double> detailsPriceColumn;
    @FXML
    private TableColumn<TransactionDetails, Integer> detailsTvaColumn;
    @FXML
    private TableColumn<TransactionDetails, Double> detailsPriceNoTvaColumn;
    @FXML
    private TableColumn<TransactionDetails, Integer> detailsDiscountColumn;
    @FXML
    private TableColumn<TransactionDetails, Double> detailsTotalColumn;
    @FXML
    private Button addDetailsButton;
    @FXML
    private Button deleteDetailsButton;

    @FXML
    private Button refreshDetailsButton;
    @FXML
    private TextField priceTextField;
    @FXML
    private TextField tvaTextField;
    @FXML
    private TextField discountTextField;
    @FXML
    private TextField transactionIdTextField;


    @FXML
    private TableView<Suppliers> suppliersTable;
    @FXML
    private TableColumn<Suppliers, Integer> supplierIdColumn;
    @FXML
    private TableColumn<Suppliers, String> supplierNameColumn;
    @FXML
    private TableColumn<Suppliers, String> supplierAddressColumn;
    @FXML
    private TableColumn<Suppliers, String> supplierPhoneColumn;
    @FXML
    private TableColumn<Suppliers, String> supplierCUIColumn;

    @FXML
    private TextField nameTextField;
    @FXML
    private TextField addressTextField;
    @FXML
    private TextField phoneTextField;
    @FXML
    private TextField cuiTextField;
    @FXML
    private Button addSuppliersButton;
    @FXML
    private Button deleteSuppliersButton;
    @FXML
    private Button refreshSuppliersButton;


    @FXML
    private Label userEmailLabel;

    private String userEmail;


    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.idColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
        this.amountColumn.setCellValueFactory(cellData -> cellData.getValue().amountProperty().asObject());
        this.dateColumn.setCellValueFactory(cellData -> cellData.getValue().dateProperty());
        this.descriptionColumn.setCellValueFactory(cellData -> cellData.getValue().descriptionProperty());
        this.measureUnitColumn.setCellValueFactory(cellData -> cellData.getValue().measureUnitProperty());
        this.suppliertranzNameColumn.setCellValueFactory(cellData -> cellData.getValue().supplierNameProperty());

        this.detailsTransactionIdColumn.setCellValueFactory(cellData -> cellData.getValue().transactionIdProperty().asObject());
        this.detailsPriceColumn.setCellValueFactory(cellData -> cellData.getValue().priceProperty().asObject());
        this.detailsTvaColumn.setCellValueFactory(cellData -> cellData.getValue().tvaProperty().asObject());
        this.detailsPriceNoTvaColumn.setCellValueFactory(cellData -> cellData.getValue().priceNoTvaProperty().asObject());
        this.detailsDiscountColumn.setCellValueFactory(cellData -> cellData.getValue().discountProperty().asObject());
        this.detailsTotalColumn.setCellValueFactory(cellData -> cellData.getValue().totalProperty().asObject());

        this.supplierIdColumn.setCellValueFactory(cellData -> cellData.getValue().idsupProperty().asObject());
        this.supplierNameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        this.supplierPhoneColumn.setCellValueFactory(cellData -> cellData.getValue().phoneProperty());
        this.supplierAddressColumn.setCellValueFactory(cellData -> cellData.getValue().addressProperty());
        this.supplierCUIColumn.setCellValueFactory(cellData -> cellData.getValue().CuiProperty());

        this.userEmailLabel.setText("Welcome to BallanceBuddy: " + this.userEmail);
        this.loadDataFromDatabase();
        this.loadDataDetailsFromDatabase();
        this.loadSuppliersFromDatabase();
        this.addTransactionButton.setOnAction(event -> this.addTransaction());
        this.deleteTransactionButton.setOnAction(event -> this.deleteTransaction());
        this.refreshButton.setOnAction(event -> this.refreshDataFromDatabase());
        this.addDetailsButton.setOnAction(event -> this.addTransactionDetails());
        this.deleteDetailsButton.setOnAction(event -> this.deleteTransactionDetails());
        this.refreshDetailsButton.setOnAction(event -> this.refreshDataDetailsFromDatabase());
        this.addSuppliersButton.setOnAction(event -> this.addSuppliers());
        this.deleteSuppliersButton.setOnAction(event -> this.deleteSuppliers());
        this.refreshSuppliersButton.setOnAction(event -> this.refreshSuppliersFromDatabase());
        updateDateTextField();


    }

    private void updateDateTextField() {
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        dateTextField.setText(currentDate.format(formatter));
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

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
                            String supplierName = resultSet.getString("supplier_name");
                            Transaction transaction = new Transaction(id, transactionDate, description, amount, measureUnit, supplierName);

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
            int id = this.idTextField.getText().isEmpty() ? (int) 1.0 : Integer.parseInt(this.idTextField.getText());
            String dateText = this.dateTextField.getText();
            Date date = Date.valueOf(dateText);
            String description = this.descriptionTextField.getText();
            String measureUnit = this.measureUnitTextField.getText();
            String supplierName = this.suppliertranzNameTextField.getText();
            double amount = this.amountTextField.getText().isEmpty() ? 1.0 : Double.parseDouble(this.amountTextField.getText());

            // Check if the supplier exists in the "suppliers" table
            boolean supplierExists = checkSupplierExistence(supplierName);


            if (!supplierExists) {
                // Prompt the user to add the supplier
                showAlert("Supplier not found", "Please add the supplier '" + supplierName + "' before adding the transaction.");
                return; // Exit the method if the supplier doesn't exist
            }

            Transaction newTransaction = new Transaction(id, date, description, amount, measureUnit, supplierName);

            Platform.runLater(() ->
                    this.transactionTable.getItems().add(newTransaction)
            );

            boolean success = DbFunctions.addTransaction(newTransaction);
            if (success) {
                System.out.println("Transaction added successfully");
            } else {
                System.err.println("Failed to add transaction to the database");
            }
            this.idTextField.clear();
            this.dateTextField.clear();
            this.descriptionTextField.clear();
            this.amountTextField.clear();
            this.measureUnitTextField.clear();
            this.suppliertranzNameTextField.clear();
            updateDateTextField();
        } catch (IllegalArgumentException var8) {
            Logger logger = Logger.getLogger(AccountingAppController.class.getName());
            logger.log(Level.SEVERE, "Error in adding transaction", var8);
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);

        ButtonType okButton = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        alert.getButtonTypes().setAll(okButton);

        alert.showAndWait().ifPresent(response -> {
            if (response == okButton) {
                // Call the method to open the supplier tab
                openSupplierTab();
            }
        });
    }

    @FXML
    private void openSupplierTab() {
        // Assuming "suppliersTab" is the fx:id of the Suppliers tab
        Tab suppliersTab = findTabById("suppliersTab");
        if (suppliersTab != null) {
            mainTabPane.getSelectionModel().select(suppliersTab);
        }
    }

    @FXML
    private void openTransactiontab() {
        // Assuming "suppliersTab" is the fx:id of the Suppliers tab
        Tab transactionsTab = findTabById("transactionsTab");
        if (transactionsTab != null) {
            mainTabPane.getSelectionModel().select(transactionsTab);
        }
    }
    private Tab findTabById(String tabId) {
        return mainTabPane.getTabs().stream()
                .filter(tab -> tab.getId() != null && tab.getId().equals(tabId))
                .findFirst()
                .orElse(null);
    }
    private boolean checkSupplierExistence(String supplierName) {
        // Implement this method to check if the supplier exists in the "suppliers" table
        // You'll need to perform a query to the PostgreSQL database
        // Return true if the supplier exists, false otherwise
        try {
            Connection con = DbFunctions.connect();
            String query = "SELECT * FROM suppliers WHERE name = ?";

            try (PreparedStatement pst = con.prepareStatement(query)) {
                pst.setString(1, supplierName);
                try (ResultSet resultSet = pst.executeQuery()) {
                    return resultSet.next(); // Returns true if the supplier exists, false otherwise
                }
            }
        } catch (SQLException e) {
            Logger logger = Logger.getLogger(AccountingAppController.class.getName());
            logger.log(Level.SEVERE, "Error in database operation", e);
            return false;
        }
    }

    private boolean checktransactionexistance(int transactionId) {

        try {
            Connection con = DbFunctions.connect();
            String query = "SELECT * FROM transactions WHERE id = ?";

            try (PreparedStatement pst = con.prepareStatement(query)) {
                pst.setInt(1, transactionId);
                try (ResultSet resultSet = pst.executeQuery()) {
                    return resultSet.next(); // Returns true if the supplier exists, false otherwise
                }
            }
        } catch (SQLException e) {
            Logger logger = Logger.getLogger(AccountingAppController.class.getName());
            logger.log(Level.SEVERE, "Error in database operation", e);
            return false;
        }
    }
    private void addTransactionDetails() {
        try {

            int transactionId = this.transactionIdTextField.getText().isEmpty() ? (int) 0 : Integer.parseInt(this.transactionIdTextField.getText());
            double price = this.priceTextField.getText().isEmpty() ? 0 : Double.parseDouble(this.priceTextField.getText());
            int tva = this.tvaTextField.getText().isEmpty() ? (int) 0 : Integer.parseInt(this.tvaTextField.getText());
            int discount = this.discountTextField.getText().isEmpty() ? (int) 0 : Integer.parseInt(this.discountTextField.getText());
            double priceNoTva = price - (tva / 100.0) * price; // Calculate priceNoTva
            double total = price - (discount / 100.0) * price; // Calculate total
            TransactionDetails newDetails = new TransactionDetails(transactionId, price, tva, priceNoTva, discount, total);
            boolean transactionExists = checktransactionexistance(transactionId);

            if (!transactionExists) {
                showAlertTransaction("Transaction not found", "Please add transaction '" + transactionId + "' before adding the transaction details.");
                return;
            }
            Platform.runLater(() ->
                    this.transactionDetailsTable.getItems().add(newDetails)
            );


            boolean success = DbFunctions.addTransactionDetails(newDetails);
            if (success) {
                System.out.println("Transaction added successfully");
            } else {
                System.err.println("Failed to add transaction to the database");
            }
            this.transactionIdTextField.clear();
            this.priceTextField.clear();
            this.tvaTextField.clear();
            this.discountTextField.clear();
        } catch (IllegalArgumentException var8) {
            Logger logger = Logger.getLogger(AccountingAppController.class.getName());
            logger.log(Level.SEVERE, "Error in adding transaction", var8);
        }
    }
    private void showAlertTransaction(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);

        ButtonType okButton = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        alert.getButtonTypes().setAll(okButton);

        alert.showAndWait().ifPresent(response -> {
            if (response == okButton) {
                // Call the method to open the supplier tab
                openTransactiontab();
            }
        });
    }

    private void deleteTransactionDetails() {
        TransactionDetails selectedTransactionDet = transactionDetailsTable.getSelectionModel().getSelectedItem();
        if (selectedTransactionDet != null) {
            boolean success = DbFunctions.deleteTransactionDetails(selectedTransactionDet.getTransactionId());
            if (success) {
                transactionDetailsTable.getItems().remove(selectedTransactionDet);
                System.out.println("Transaction deleted successfully");
            } else {
                System.err.println("Failed to delete transaction from the database");
            }
        } else {
            System.err.println("No transaction selected for deletion");
        }
    }

    private void loadDataDetailsFromDatabase() {
        String query = "SELECT * FROM transaction_details";

        try {
            Connection con = DbFunctions.connect();

            try {
                PreparedStatement pst = con.prepareStatement(query);

                try {
                    ResultSet resultSet = pst.executeQuery();

                    try {
                        while (resultSet.next()) {
                            int transactionId = resultSet.getInt("transactionid");
                            double price = resultSet.getDouble("price");
                            int tva = resultSet.getInt("tva");
                            int discount = resultSet.getInt("discount");
                            double priceNoTva = resultSet.getDouble("price_no_tva");
                            double total = resultSet.getDouble("total");

                            TransactionDetails transactionDetails = new TransactionDetails(transactionId, price, tva, priceNoTva, discount, total);

                            Platform.runLater(() ->
                                    this.transactionDetailsTable.getItems().add(transactionDetails)
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

    private void refreshDataDetailsFromDatabase() {
        // Clear existing data
        this.transactionDetailsTable.getItems().clear();

        // Reload data from the database
        loadDataDetailsFromDatabase();
    }

    private void addSuppliers() {
        try {
            int idsup = 0;
            String name = this.nameTextField.getText();
            String address = this.addressTextField.getText();
            String phone = this.phoneTextField.getText();
            String cui = this.cuiTextField.getText();
            Suppliers newSupplier = new Suppliers(idsup, name, address, phone, cui);

            Platform.runLater(() ->
                    this.suppliersTable.getItems().add(newSupplier)
            );

            boolean success = DbFunctions.addSupplier(newSupplier);
            if (success) {
                System.out.println("Supplier added successfully");
            } else {
                System.err.println("Failed to add supplier to the database");
            }
            this.nameTextField.clear();
            this.addressTextField.clear();
            this.phoneTextField.clear();
            this.cuiTextField.clear();

        } catch (IllegalArgumentException var20) {
            Logger logger = Logger.getLogger(AccountingAppController.class.getName());
            logger.log(Level.SEVERE, "Error in adding supplier", var20);
        }
    }

    private void deleteSuppliers() {
        Suppliers selectedSupplier = suppliersTable.getSelectionModel().getSelectedItem();
        if (selectedSupplier != null) {
            boolean success = DbFunctions.deletesupplier(selectedSupplier.getIdsup());
            if (success) {
                suppliersTable.getItems().remove(selectedSupplier);
                System.out.println("Supplier deleted successfully");
            } else {
                System.err.println("Failed to delete supplier from the database");
            }
        } else {
            System.err.println("No supplier selected for deletion");
        }
    }

    private void loadSuppliersFromDatabase() {
        String query = "SELECT * FROM suppliers";

        try {
            Connection con = DbFunctions.connect();

            try {
                PreparedStatement pst = con.prepareStatement(query);

                try {
                    ResultSet resultSet = pst.executeQuery();

                    try {
                        while (resultSet.next()) {
                            int idsup = resultSet.getInt("id");
                            String name = resultSet.getString("name");
                            String address = resultSet.getString("address");
                            String phone = resultSet.getString("phone_number");
                            String cui = resultSet.getString("cui");
                            Suppliers suppliers = new Suppliers(idsup, name, address, phone, cui);

                            Platform.runLater(() ->
                                    this.suppliersTable.getItems().add(suppliers)
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
    public void refreshSuppliersFromDatabase() {
        this.suppliersTable.getItems().clear();
        // Reload data from the database
        loadSuppliersFromDatabase();
    }


}

