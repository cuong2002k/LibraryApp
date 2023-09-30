package com.example.ebookapp.Model;

import java.io.Serializable;
import java.util.Date;

public class Borrowing implements Serializable {
    int borrowingId;
    int readerId;
    Date borrowDay;
    Date returnDay;
    Date returnTime;

    public Borrowing() {
    }

    public Borrowing(int readerId, Date borrowDay, Date returnDay, Date returnTime) {
        this.readerId = readerId;
        this.borrowDay = borrowDay;
        this.returnDay = returnDay;
        this.returnTime = returnTime;
    }

    public Borrowing(int borrowingId, int readerId, Date borrowDay, Date returnDay, Date returnTime) {
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

    public Date getBorrowDay() {
        return borrowDay;
    }

    public void setBorrowDay(Date borrowDay) {
        this.borrowDay = borrowDay;
    }

    public Date getReturnDay() {
        return returnDay;
    }

    public void setReturnDay(Date returnDay) {
        this.returnDay = returnDay;
    }

    public Date getReturnTime() {
        return returnTime;
    }

    public void setReturnTime(Date returnTime) {
        this.returnTime = returnTime;
    }






}
