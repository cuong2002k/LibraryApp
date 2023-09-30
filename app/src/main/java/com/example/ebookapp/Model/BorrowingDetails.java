package com.example.ebookapp.Model;

import java.io.Serializable;

public class BorrowingDetails implements Serializable {
    int borrowingId;
    int bookId;

    public BorrowingDetails() {
    }

    public int getBorrowingId() {
        return borrowingId;
    }

    public void setBorrowingId(int borrowingId) {
        this.borrowingId = borrowingId;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public BorrowingDetails(int borrowingId, int bookId) {
        this.borrowingId = borrowingId;
        this.bookId = bookId;
    }
}
