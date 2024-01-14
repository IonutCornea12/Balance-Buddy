package com.example.BalanceBuddy;
import javafx.beans.property.SimpleIntegerProperty;

public class SupplierTransaction {
    private final SimpleIntegerProperty supplierId;
    private final SimpleIntegerProperty transactionId;

    public SupplierTransaction(int supplierId, int transactionId) {
        this.supplierId = new SimpleIntegerProperty(supplierId);
        this.transactionId = new SimpleIntegerProperty(transactionId);
    }

    public SimpleIntegerProperty suppliertransIdProperty() {
        return supplierId;
    }

    public int getSuppliertransId() {
        return supplierId.get();
    }

    public SimpleIntegerProperty transactionsuppIdProperty() {
        return transactionId;
    }

    public int getTransactionsuppId() {
        return transactionId.get();
    }
}
