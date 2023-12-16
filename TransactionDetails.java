package com.example.BalanceBuddy;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TransactionDetails {
    private final SimpleIntegerProperty transactionId;
    private final SimpleDoubleProperty price;
    private final SimpleIntegerProperty tva;
    private final SimpleDoubleProperty priceNoTva;
    private final SimpleIntegerProperty discount;
    private final SimpleDoubleProperty total;


    public TransactionDetails(int transactionId, double price, int tva, double priceNoTva, int discount, double total) {
        this.transactionId = new SimpleIntegerProperty(transactionId);
        this.price = new SimpleDoubleProperty(price);
        this.tva = new SimpleIntegerProperty(tva);
        this.priceNoTva = new SimpleDoubleProperty(priceNoTva);
        this.discount = new SimpleIntegerProperty(discount);
        this.total = new SimpleDoubleProperty(total);
    }

    public boolean addTransactionDetails() {
        try {
            Connection connection = DbFunctions.connect();
            try {
                String query = "INSERT INTO transactions_details (transaction_id, price, tva, price_no_tva, discount, total) VALUES (?, ?, ?, ?, ?, ?)";
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setInt(1, this.getTransactionId());
                statement.setDouble(2, this.getPrice());
                statement.setInt(3, this.getTva());
                statement.setDouble(4, this.getPriceNoTva());
                statement.setInt(5, this.getDiscount());
                statement.setDouble(6, this.getTotal());
                int rowsAffected = statement.executeUpdate();
                return rowsAffected > 0;
            } finally {
                if (connection != null) {
                    connection.close();
                }
            }
        } catch (SQLException e) {
            Logger logger = Logger.getLogger(TransactionDetails.class.getName());
            logger.log(Level.SEVERE, "Error adding transaction details", e);
            return false;
        }
    }


    public SimpleIntegerProperty transactionIdProperty() {return transactionId;}

    public int getTransactionId() {return transactionId.get();}

    public double getPrice() {return price.get();}

    public SimpleDoubleProperty priceProperty() {return price;}

    public int getTva() {return tva.get();}

    public SimpleIntegerProperty tvaProperty() {
        return tva;
    }

    public double getPriceNoTva() {
        return priceNoTva.get();
    }

    public SimpleDoubleProperty priceNoTvaProperty() {
        return priceNoTva;
    }

    public int getDiscount() {return discount.get();}

    public SimpleIntegerProperty discountProperty() {
        return discount;
    }

    public double getTotal() {return total.get();}

    public SimpleDoubleProperty totalProperty() {
        return total;
    }

    // Optional: You may want to override the toString() method for better display
    @Override
    public String toString() {
        return "TransactionDetails{" +
                ", transactionId=" + transactionId +
                ", price=" + price +
                ", tva=" + tva +
                ", priceNoTva=" + priceNoTva +
                ", discount=" + discount +
                ", total=" + total +
                '}';
    }
}
