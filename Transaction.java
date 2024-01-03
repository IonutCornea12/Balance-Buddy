package com.example.BalanceBuddy;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import java.sql.Date;

public class Transaction {
    private final SimpleIntegerProperty id;
    private final SimpleObjectProperty<Date> date;
    private final SimpleStringProperty description;
    private final SimpleDoubleProperty amount;
    private final SimpleStringProperty measureUnit;

    private final SimpleStringProperty supplierName;


    public Transaction(int id, Date date, String description, double amount, String measureUnit, String supplierName) {
        this.id = new SimpleIntegerProperty(id);
        this.date = new SimpleObjectProperty<>(date);
        this.description = new SimpleStringProperty(description);
        this.amount = new SimpleDoubleProperty(amount);
        this.measureUnit = new SimpleStringProperty(measureUnit);
        this.supplierName = new SimpleStringProperty(supplierName);
    }
     public SimpleObjectProperty<Date> dateProperty() {
        return date;
    }

    public Date getDate() {
        return date.get();
    }

    public SimpleIntegerProperty idProperty() {return id;}

    public int getId() {return id.get();}

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

    public String getsupplierName() {
        return supplierName.get();
    }

    public SimpleStringProperty supplierNameProperty() {
        return supplierName;
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
                ", suppliers=" + supplierName +
                '}';
    }

}
