package com.example.sliit;

import java.sql.Time;

public class AddOrderDetails {
   // private String ID;
    private int quantity;
    private String spinner1;
    private Time time;
    private String spinner2;

    public AddOrderDetails() {
    }

  /*  public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    } */

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getSpinner1() {
        return spinner1;
    }

    public void setSpinner1(String spinner1) {
        this.spinner1= spinner1 ;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public String getSpinner2() {
        return spinner2;
    }

    public void setSpinner2(String spinner2) {
        this.spinner2= spinner2;
    }
}
