package com.example.BalanceBuddy;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import java.sql.Date;

public class Suppliers {
    private final SimpleIntegerProperty idsup;
    private final SimpleStringProperty name;
    private final SimpleStringProperty address;
    private final SimpleStringProperty phone;
    private final SimpleStringProperty cui;

    public Suppliers(int idsup, String name, String address, String phone, String cui) {
        this.idsup  = new SimpleIntegerProperty(idsup);
        this.name = new SimpleStringProperty(name);
        this.address = new SimpleStringProperty(address);
        this.phone = new SimpleStringProperty(phone);
        this.cui = new SimpleStringProperty(cui);
    }

    public SimpleIntegerProperty idsupProperty() {return idsup;}
    public int getIdsup() {return idsup.get();}




    public SimpleStringProperty nameProperty() {
        return name;
    }
    public String getname() {
        return name.get();
    }

    public String getaddress() {return address.get();}

    public SimpleStringProperty addressProperty() {return address;}
    public String getphone(){ return phone.get();}
    public SimpleStringProperty phoneProperty() {return phone;}

    public String getcui() {return cui.get();}

    public SimpleStringProperty CuiProperty() {return cui;}


    @Override
    public String toString() {
        return "Suppliers{" +
                "idsup=" + idsup +
                "name=" + name +
                ", address=" + address +
                ", phone=" + phone +
                ", cui=" + cui +
                '}';
    }

}
