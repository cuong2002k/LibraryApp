package com.example.ebookapp.Model;

import java.io.Serializable;
import java.util.Date;

public class Borrowing implements Serializable {
    int borrowingId;
    int readerId;
    String borrowDay;
    String returnDay;
    String returnTime;

    public Borrowing() {
    }

    public Borrowing(int readerId, String borrowDay, String returnDay, String returnTime) {
        this.readerId = readerId;
        this.borrowDay = borrowDay;
        this.returnDay = returnDay;
        this.returnTime = returnTime;
    }

    public Borrowing(int borrowingId, int readerId, String borrowDay, String returnDay, String returnTime) {
        this.borrowingId = borrowingId;
        this.readerId = readerId;
        this.borrowDay = borrowDay;
        this.returnDay = returnDay;
        this.returnTime = returnTime;
    }

    public int getBorrowingId() {
        return borrowingId;
    }

    public void setBorrowingId(int borrowingId) {
        this.borrowingId = borrowingId;
    }

    public int getReaderId() {
        return readerId;
    }

    public void setReaderId(int readerId) {
        this.readerId = readerId;
    }

    public String getBorrowDay() {
        return borrowDay;
    }

    public void setBorrowDay(String borrowDay) {
        this.borrowDay = borrowDay;
    }

    public String getReturnDay() {
        return returnDay;
    }

    public void setReturnDay(String returnDay) {
        this.returnDay = returnDay;
    }

    public String getReturnTime() {
        return returnTime;
    }

    public void setReturnTime(String returnTime) {
        this.returnTime = returnTime;
    }






}
