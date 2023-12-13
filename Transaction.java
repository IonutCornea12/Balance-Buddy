package com.example.proiect2;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Transaction {
    private final SimpleIntegerProperty id;
    private final SimpleObjectProperty<Date> date;
    private final SimpleStringProperty description;
    private final SimpleDoubleProperty amount;
    private final SimpleStringProperty measureUnit;

    public Transaction(int id, Date date, String description, double amount, String measureUnit) {
        this.id = new SimpleIntegerProperty(id);
        this.date = new SimpleObjectProperty<>(date);
        this.description = new SimpleStringProperty(description);
        this.amount = new SimpleDoubleProperty(amount);
        this.measureUnit = new SimpleStringProperty(measureUnit);
    }

    public boolean deleteTransaction() {
        try {
            Connection connection = DbFunctions.connect();
            try {
                String query = "DELETE FROM transactions WHERE id = ?";
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setInt(1, this.getId());
                int rowsAffected = statement.executeUpdate();
                return rowsAffected > 0;
            } finally {
                if (connection != null) {
                    connection.close();
                }
            }
        } catch (SQLException e) {
            Logger logger = Logger.getLogger(Transaction.class.getName());
            logger.log(Level.SEVERE, "Error deleting transaction", e);
            return false;
        }
    }

    public SimpleObjectProperty<Date> dateProperty() {
        return date;
    }

    public Date getDate() {
        return date.get();
    }

    public SimpleIntegerProperty idProperty() {
        return id;
    }

    public int getId() {
        return id.get();
    }

    public String getDescription() {
        return description.get();
    }

    public SimpleStringProperty descriptionProperty() {
        return description;
    }

    public double getAmount() {
        return amount.get();
    }

    public SimpleDoubleProperty amountProperty() {
        return amount;
    }

    public String getMeasureUnit() {
        return measureUnit.get();
    }

    public SimpleStringProperty measureUnitProperty() {
        return measureUnit;
    }

    // Optional: You may want to override the toString() method for better display in the table
    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                "date=" + date +
                ", description=" + description +
                ", amount=" + amount +
                ", measureUnit=" + measureUnit +
                '}';
    }

}
