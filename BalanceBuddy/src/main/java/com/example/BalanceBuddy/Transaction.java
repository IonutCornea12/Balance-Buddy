package com.example.BalanceBuddy;

import javafx.beans.property.*;

import java.sql.Date;

public class Transaction {
    private final SimpleIntegerProperty id;
    private final SimpleObjectProperty<Date> date;
    private final SimpleStringProperty description;
    private final SimpleFloatProperty amount;
    private final SimpleStringProperty measureUnit;

    private final SimpleStringProperty supplierName;


    public Transaction(int id, Date date, String description, float amount, String measureUnit, String supplierName) {
        this.id = new SimpleIntegerProperty(id);
        this.date = new SimpleObjectProperty<>(date);
        this.description = new SimpleStringProperty(description);
        this.amount = new SimpleFloatProperty(amount);
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

    public float getAmount() {
        return amount.get();
    }

    public SimpleFloatProperty amountProperty() {
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
