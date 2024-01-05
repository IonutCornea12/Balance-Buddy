package com.example.BalanceBuddy;

import javafx.application.Platform;


import javafx.event.ActionEvent;
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
import java.util.Optional;
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
    private TableColumn<Transaction, Float> amountColumn;
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
    private TableColumn<TransactionDetails, Float> detailsPriceColumn;
    @FXML
    private TableColumn<TransactionDetails, Integer> detailsTvaColumn;
    @FXML
    private TableColumn<TransactionDetails, Float> detailsPriceNoTvaColumn;
    @FXML
    private TableColumn<TransactionDetails, Integer> detailsDiscountColumn;
    @FXML
    private TableColumn<TransactionDetails, Float> detailsTotalColumn;
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

    @FXML
    private TableView<Summary> summaryTable;
    @FXML
    private TableColumn<Summary, Integer> idColumnSummary;

    @FXML
    private TableColumn<Summary, String> dateColumnSummary;

    @FXML
    private TableColumn<Summary, Float> totalColumnSummary;

    @FXML
    private TextField dateTextFieldSummary;

    @FXML
    private Button addSummaryButton;
    @FXML
    private Button deleteSummaryButton;
    @FXML
    private Button refreshSummaryButton;
    @FXML
    private Button updateSummaryButton;
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
        this.loadSummaryDataFromDatabase();
        this.addTransactionButton.setOnAction(event -> this.addTransaction());
        this.deleteTransactionButton.setOnAction(event -> this.deleteTransaction());
        this.refreshButton.setOnAction(event -> this.refreshDataFromDatabase());
        this.addDetailsButton.setOnAction(event -> this.addTransactionDetails());
        this.deleteDetailsButton.setOnAction(event -> this.deleteTransactionDetailsonly());
        this.refreshDetailsButton.setOnAction(event -> this.refreshDataDetailsFromDatabase());
        this.addSuppliersButton.setOnAction(event -> this.addSuppliers());
        this.deleteSuppliersButton.setOnAction(event -> this.deleteSuppliers());
        this.refreshSuppliersButton.setOnAction(event -> this.refreshSuppliersFromDatabase());
        updateDateTextField();

        this.idColumnSummary.setCellValueFactory(cellData -> cellData.getValue().idPropertySum().asObject());
        this.dateColumnSummary.setCellValueFactory(cellData -> cellData.getValue().datePropertySum().asString());
        this.totalColumnSummary.setCellValueFactory(cellData -> cellData.getValue().totalPropertySum().asObject());
        this.addSummaryButton.setOnAction(event -> this.addSummaryToDatabase());
        this.deleteSummaryButton.setOnAction(event -> this.deleteSummary());
        this.refreshSummaryButton.setOnAction(event -> this.refreshSummaryFromDatabase());
        this.updateSummaryButton.setOnAction(event -> this.updateSummary());

    }

    private void updateDateTextField() {
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        dateTextField.setText(currentDate.format(formatter));
    }

    private Tab findTabById(String tabId) {
        return mainTabPane.getTabs().stream()
                .filter(tab -> tab.getId() != null && tab.getId().equals(tabId))
                .findFirst()
                .orElse(null);
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
        Tab transactionsTab = findTabById("transactionsTab");
        if (transactionsTab != null) {
            mainTabPane.getSelectionModel().select(transactionsTab);
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
                openSupplierTab();
            }
        });
    }

    private void showAlertTransactionDet(String title, String content) {
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

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    private void addTransaction() {
        try {
            int id = this.idTextField.getText().isEmpty() ? (int) 1.0 : Integer.parseInt(this.idTextField.getText());
            String dateText = this.dateTextField.getText();
            Date date = Date.valueOf(dateText);
            String description = this.descriptionTextField.getText();
            String measureUnit = this.measureUnitTextField.getText();
            String supplierName = this.suppliertranzNameTextField.getText();
            float amount = this.amountTextField.getText().isEmpty() ? 1 : Float.parseFloat(this.amountTextField.getText());

            // Check if the supplier exists in the "suppliers" table
            boolean supplierExists = checkSupplierExistence(supplierName);


            if (!supplierExists) {
                // Prompt the user to add the supplier
                showAlertTransaction("Supplier not found", "Please add the supplier '" + supplierName + "' before adding the transaction.");
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
                            float amount = resultSet.getFloat("amount");
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

    private void deleteTransaction() {
        Transaction selectedTransaction = transactionTable.getSelectionModel().getSelectedItem();
        if (selectedTransaction != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Dialog");
            alert.setHeaderText("Delete Transaction");
            alert.setContentText("Are you sure you want to delete the selected transaction and its details?");

            ButtonType yesButton = new ButtonType("Yes", ButtonBar.ButtonData.YES);
            ButtonType noButton = new ButtonType("No", ButtonBar.ButtonData.NO);

            alert.getButtonTypes().setAll(yesButton, noButton);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == yesButton) {
                boolean success = deleteTransactionAndDetails(selectedTransaction);
                if (success) {
                    transactionTable.getItems().remove(selectedTransaction);
                    System.out.println("Transaction and details deleted successfully");
                } else {
                    System.err.println("Failed to delete transaction from the database");
                }
            } else {
                System.out.println("Deletion canceled");
            }
        } else {
            System.err.println("No transaction selected for deletion");
        }
        refreshDataFromDatabase();
    }

    private boolean deleteTransactionAndDetails(Transaction selectedTransaction) {
        int transactionId = selectedTransaction.getId();
        boolean transactionDeleted = DbFunctions.deleteTransaction(transactionId);

        if (transactionDeleted) {
            deleteTransactionDetails(selectedTransaction);
            return true;
        } else {
            System.out.println("Failed to delete transaction.");
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
            int transactionId = this.transactionIdTextField.getText().isEmpty() ? 0 : Integer.parseInt(this.transactionIdTextField.getText());
            float price = this.priceTextField.getText().isEmpty() ? 0 : Float.parseFloat(this.priceTextField.getText());
            int tva = this.tvaTextField.getText().isEmpty() ? 0 : Integer.parseInt(this.tvaTextField.getText());
            int discount = this.discountTextField.getText().isEmpty() ? 0 : Integer.parseInt(this.discountTextField.getText());

            // Check if the transaction exists
            boolean transactionExists = checktransactionexistance(transactionId);

            if (!transactionExists) {
                showAlertTransactionDet("Transaction not found", "Please add transaction '" + transactionId + "' before adding the transaction details.");
                return;
            }

            // Find the corresponding transaction to get the amount
            Transaction correspondingTransaction = getCorrespondingTransaction(transactionId);

            if (correspondingTransaction != null) {
                float amount = correspondingTransaction.getAmount();
                float aux = amount * price;
                // Calculate total using the amount from the transaction and price
                float total = aux - (discount / 100.0f) * aux;



                // Create TransactionDetails with calculated total
                TransactionDetails newDetails = new TransactionDetails(transactionId, price, tva, price - (tva / 100.0f) * price, discount, total);

                Platform.runLater(() ->
                        this.transactionDetailsTable.getItems().add(newDetails)
                );

                boolean success = DbFunctions.addTransactionDetails(newDetails);
                if (success) {
                    System.out.println("Transaction details added successfully");
                } else {
                    System.err.println("Failed to add transaction details to the database");
                }
            } else {
                System.err.println("No corresponding transaction found");
            }

            this.transactionIdTextField.clear();
            this.priceTextField.clear();
            this.tvaTextField.clear();
            this.discountTextField.clear();
        } catch (IllegalArgumentException var8) {
            Logger logger = Logger.getLogger(AccountingAppController.class.getName());
            logger.log(Level.SEVERE, "Error in adding transaction details", var8);
        }
    }

    private Transaction getCorrespondingTransaction(int transactionId) {

        try {
            Connection con = DbFunctions.connect();
            String query = "SELECT * FROM transactions WHERE id = ?";

            try (PreparedStatement pst = con.prepareStatement(query)) {
                pst.setInt(1, transactionId);
                try (ResultSet resultSet = pst.executeQuery()) {
                    if (resultSet.next()) {
                        int id = resultSet.getInt("id");
                        Date transactionDate = resultSet.getDate("transaction_date");
                        String description = resultSet.getString("description");
                        float amount = resultSet.getFloat("amount");
                        String measureUnit = resultSet.getString("measure_unit");
                        String supplierName = resultSet.getString("supplier_name");
                        return new Transaction(id, transactionDate, description, amount, measureUnit, supplierName);
                    } else {
                        return null; // No corresponding transaction found
                    }
                }
            }
        } catch (SQLException e) {
            Logger logger = Logger.getLogger(AccountingAppController.class.getName());
            logger.log(Level.SEVERE, "Error in database operation", e);
            return null;
        }
    }

    private void deleteTransactionDetailsonly() {
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

    private void deleteTransactionDetails(Transaction selectedTransaction) {
        int transactionId = selectedTransaction.getId();
        boolean success = DbFunctions.deleteTransactionDetails(transactionId);

        if (success) {
            refreshDataDetailsFromDatabase();
            System.out.println("Transaction details deleted successfully.");
        } else {
            System.out.println("Failed to delete transaction details.");
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
                            float price = resultSet.getFloat("price");
                            int tva = resultSet.getInt("tva");
                            int discount = resultSet.getInt("discount");
                            float priceNoTva = resultSet.getFloat("price_no_tva");
                            float total = resultSet.getFloat("total");

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

    private void addSummaryToDatabase() {
        String selectedDate = dateTextFieldSummary.getText();
        int idsum = 0 ;
        
        Summary newSummary = new Summary(idsum, Date.valueOf(selectedDate), getSummaryTotalFromDatabase(selectedDate));

        boolean success = DbFunctions.addSummary(newSummary);
        if (success) {
            System.out.println("Summary added successfully to the database");
        } else {
            System.err.println("Failed to add summary to the database");
        }
        refreshSummaryFromDatabase();
    }

    private float getSummaryTotalFromDatabase(String selectedDate) {
        String query = "SELECT SUM(total) as total_price " +
                "FROM transactions t " +
                "JOIN transaction_details td ON t.id = td.transactionid " +
                "WHERE transaction_date = ? " +
                "GROUP BY transaction_date;";

        try (Connection con = DbFunctions.connect();
             PreparedStatement pst = con.prepareStatement(query)) {

            pst.setDate(1, java.sql.Date.valueOf(selectedDate));

            try (ResultSet resultSet = pst.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getFloat("total_price");
                }
            }
        } catch (SQLException e) {
            Logger logger = Logger.getLogger(AccountingAppController.class.getName());
            logger.log(Level.SEVERE, "Error in database operation", e);
        }

        return 0; // Default value if something goes wrong
    }

    private void loadSummaryDataFromDatabase() {
        String query = "SELECT * FROM summary";

        try {
            Connection con = DbFunctions.connect();

            try {
                PreparedStatement pst = con.prepareStatement(query);

                try {
                    ResultSet resultSet = pst.executeQuery();

                    try {
                        while (resultSet.next()) {
                            int idsum = resultSet.getInt("id");
                            Date date = resultSet.getDate("date");
                            float total = resultSet.getFloat("total");
                            Summary summary = new Summary(idsum,date,total);

                            Platform.runLater(() ->
                                    this.summaryTable.getItems().add(summary)
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

    private void deleteSummary() {
        Summary selectedSummary = summaryTable.getSelectionModel().getSelectedItem();
        if (selectedSummary!= null) {
            boolean success = DbFunctions.deleteSummary(selectedSummary.getIdSum());
            if (success) {
                summaryTable.getItems().remove(selectedSummary);
                System.out.println("Summary deleted successfully");
            } else {
                System.err.println("Failed to delete summary from the database");
            }
        } else {
            System.err.println("No summary selected for deletion");
        }
    }

    private void refreshSummaryFromDatabase() {
        // Clear existing data
        this.summaryTable.getItems().clear();

        // Reload data from the database
        loadSummaryDataFromDatabase();
    }

    private void updateSummary() {
        Summary selectedSummary = summaryTable.getSelectionModel().getSelectedItem();
        if (selectedSummary != null) {
            // Get the selected idsum and date
            int selectedIdSum = selectedSummary.getIdSum();
            Date selectedDate = selectedSummary.getDateSum();

            // Delete the existing summary
            boolean deleteSuccess = DbFunctions.deleteSummary(selectedIdSum);

            if (deleteSuccess) {
                System.out.println("Summary deleted successfully for the selected idsum");

                // Add a new summary with the same date
                addSummaryToDatabase2(selectedDate);
            } else {
                System.err.println("Failed to delete summary for the selected idsum from the database");
            }
        } else {
            System.err.println("No summary selected for deletion and addition");
        }
    }

    private void addSummaryToDatabase2(Date selectedDate) {
        int idsum = 0;


        Summary newSummary = new Summary(idsum, selectedDate, getSummaryTotalFromDatabase(selectedDate.toString()));

        boolean success = DbFunctions.addSummary(newSummary);
        if (success) {
            System.out.println("Summary added successfully to the database");
        } else {
            System.err.println("Failed to add summary to the database");
        }
        refreshSummaryFromDatabase();
    }








}

