package com.sebasth.alifood.models;

import java.time.LocalDate;

public class Food {
    private int id;
    private String name;
    private LocalDate inDate;
    private LocalDate expDate;
    private boolean inFridge;
    private boolean isbasic;
    private boolean autoDelete;

    // Create a constructor for the Food class
    public Food(int id, String name, LocalDate inDate, LocalDate expDate, boolean inFridge, boolean isbasic, boolean autoDelete) {
        this.id = id;
        this.name = name;
        this.inDate = inDate;
        this.expDate = expDate;
        this.inFridge = inFridge;
        this.isbasic = isbasic;
        this.autoDelete = autoDelete;
    }

    // Constructor to new Food object without id
    public Food(String name, LocalDate inDate, LocalDate expDate, boolean inFridge, boolean isbasic) {
        this(-1, name, inDate, expDate, inFridge, isbasic, false); // -1 to indicate no id on bd
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getInDate() {
        return inDate;
    }

    public void setInDate(LocalDate inDate) {
        this.inDate = inDate;
    }

    public LocalDate getExpDate() {
        return expDate;
    }

    public void setExpDate(LocalDate expDate) {
        this.expDate = expDate;
    }

    public boolean isInFridge() {
        return inFridge;
    }

    public void setInFridge(boolean inFridge) {
        this.inFridge = inFridge;
    }

    public boolean isIsbasic() {
        return isbasic;
    }

    public void setIsbasic(boolean isbasic) {
        this.isbasic = isbasic;
    }

    public boolean isAutoDelete() {
        return autoDelete;
    }

    public void setAutoDelete(boolean autoDelete) {
        this.autoDelete = autoDelete;
    }

    @Override
    public String toString() {
        return "Food{" +
                "# = " + id +
                ", Nombre = ' " + name + '\'' +
                ", Fecha de ingreso = " + inDate +
                ", Fecha de Caducida = " + expDate +
                ", En Nevera = " + inFridge +
                ", Es Basico = " + isbasic +
                ", Eliminado Automaticamente = " + autoDelete +
                '}';
    }
}
