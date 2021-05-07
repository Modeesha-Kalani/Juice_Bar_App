package com.example.fruzorest.model;

public class Order {
    private String id;
    private String pid;
    private String ptype;
    private int rqty;
    private int lqty;
    private double total;
    private int status;
    private String date;
    private String userid;
    private int hour;
    private int minuts;
    private double rprice;
    private double lprice;
    private String pname;

    public Order() {
    }

    public Order(String id, String pid, String ptype, int rqty, int lqty, double total, int status, String date, String userid, int hour, int minuts, double rprice, double lprice, String pname) {
        this.id = id;
        this.pid = pid;
        this.ptype = ptype;
        this.rqty = rqty;
        this.lqty = lqty;
        this.total = total;
        this.status = status;
        this.date = date;
        this.userid = userid;
        this.hour = hour;
        this.minuts = minuts;
        this.rprice = rprice;
        this.lprice = lprice;
        this.pname = pname;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getPtype() {
        return ptype;
    }

    public void setPtype(String ptype) {
        this.ptype = ptype;
    }

    public int getRqty() {
        return rqty;
    }

    public void setRqty(int rqty) {
        this.rqty = rqty;
    }

    public int getLqty() {
        return lqty;
    }

    public void setLqty(int lqty) {
        this.lqty = lqty;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinuts() {
        return minuts;
    }

    public void setMinuts(int minuts) {
        this.minuts = minuts;
    }

    public double getRprice() {
        return rprice;
    }

    public void setRprice(double rprice) {
        this.rprice = rprice;
    }

    public double getLprice() {
        return lprice;
    }

    public void setLprice(double lprice) {
        this.lprice = lprice;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }
}

