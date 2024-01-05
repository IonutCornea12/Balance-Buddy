package com.example.BalanceBuddy;


import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;


import java.sql.Date;

public class Summary {
    private final SimpleIntegerProperty id;
    private final SimpleObjectProperty<Date> date;
    private final SimpleFloatProperty total;

    public Summary(int id, Date date, float total) {
        this.id = new SimpleIntegerProperty(id);
        this.date = new SimpleObjectProperty<>(date);
        this.total = new SimpleFloatProperty(total);
    }

    public SimpleObjectProperty<Date> datePropertySum() {
        return date;
    }

    public Date getDateSum() {
        return date.get();
    }

    public SimpleIntegerProperty idPropertySum() {return id;}

    public int getIdSum() {return id.get();}
    public float getTotalSum() {return total.get();}
    public SimpleFloatProperty totalPropertySum() {
        return total;
    }



}

