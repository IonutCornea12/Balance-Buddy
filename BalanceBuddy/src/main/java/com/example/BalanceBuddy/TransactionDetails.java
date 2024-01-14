package com.example.BalanceBuddy;

import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TransactionDetails {
    private final SimpleIntegerProperty transactionId;
    private final SimpleFloatProperty price;
    private final SimpleIntegerProperty tva;
    private final SimpleFloatProperty priceNoTva;
    private final SimpleIntegerProperty discount;
    private final SimpleFloatProperty total;


    public TransactionDetails(int transactionId, float price, int tva, float priceNoTva, int discount, float total) {
        this.transactionId = new SimpleIntegerProperty(transactionId);
        this.price = new SimpleFloatProperty(price);
        this.tva = new SimpleIntegerProperty(tva);
        this.priceNoTva = new SimpleFloatProperty(priceNoTva);
        this.discount = new SimpleIntegerProperty(discount);
        this.total = new SimpleFloatProperty(total);
    }
    public SimpleIntegerProperty transactionIdProperty() {return transactionId;}

    public int getTransactionId() {return transactionId.get();}

    public float getPrice() {return price.get();}

    public SimpleFloatProperty priceProperty() {return price;}

    public int getTva() {return tva.get();}

    public SimpleIntegerProperty tvaProperty() {
        return tva;
    }

    public float getPriceNoTva() {
        return priceNoTva.get();
    }

    public SimpleFloatProperty priceNoTvaProperty() {
        return new SimpleFloatProperty(priceNoTva.floatValue());
    }

    public int getDiscount() {return discount.get();}

    public SimpleIntegerProperty discountProperty() {
        return discount;
    }

    public float getTotal() {return total.get();}

    public SimpleFloatProperty totalProperty() {
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
