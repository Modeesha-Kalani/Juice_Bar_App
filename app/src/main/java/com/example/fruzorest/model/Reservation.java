package com.example.fruzorest.model;

import java.util.Date;

public class Reservation {

    private String id;
    private String time;
    private String date;
    private String duration;
    private String reservationdate;
    private String userid;
    private String status;
    private String contact;
    private String reservfor;
    private int tableid;

    public Reservation() {
    }

    public Reservation(String id, String time, String date, String duration, String reservationdate, String userid, String status, String contact, String reservfor, int tableid) {
        this.id = id;
        this.time = time;
        this.date = date;
        this.duration = duration;
        this.reservationdate = reservationdate;
        this.userid = userid;
        this.status = status;
        this.contact = contact;
        this.reservfor = reservfor;
        this.tableid = tableid;
    }

    public int getTableid() {
        return tableid;
    }

    public void setTableid(int tableid) {
        this.tableid = tableid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getReservationdate() {
        return reservationdate;
    }

    public void setReservationdate(String reservationdate) {
        this.reservationdate = reservationdate;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getReservfor() {
        return reservfor;
    }

    public void setReservfor(String reservfor) {
        this.reservfor = reservfor;
    }
}
